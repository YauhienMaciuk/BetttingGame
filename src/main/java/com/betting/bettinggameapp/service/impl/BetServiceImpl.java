package com.betting.bettinggameapp.service.impl;

import com.betting.bettinggameapp.casino.GameContext;
import com.betting.bettinggameapp.casino.Roulette;
import com.betting.bettinggameapp.casino.RoundResult;
import com.betting.bettinggameapp.casino.Slot;
import com.betting.bettinggameapp.dto.AccountStateDto;
import com.betting.bettinggameapp.dto.BetDto;
import com.betting.bettinggameapp.dto.GameResultDto;
import com.betting.bettinggameapp.entity.AccountState;
import com.betting.bettinggameapp.entity.Bet;
import com.betting.bettinggameapp.entity.GameResult;
import com.betting.bettinggameapp.entity.User;
import com.betting.bettinggameapp.exception.BettingGameException;
import com.betting.bettinggameapp.exception.NoSuchEntityException;
import com.betting.bettinggameapp.lock.NamedLocks;
import com.betting.bettinggameapp.lock.Unlockable;
import com.betting.bettinggameapp.repository.BetRepository;
import com.betting.bettinggameapp.service.AccountStateService;
import com.betting.bettinggameapp.service.BetService;
import com.betting.bettinggameapp.service.GameResultService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BetServiceImpl implements BetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BetServiceImpl.class);
    private static final NamedLocks<Long> USER_LOCKS = new NamedLocks<>();

    private final BetRepository betRepository;
    private final GameResultService gameResultService;
    private final AccountStateService accountStateService;

    public BetServiceImpl(BetRepository betRepository,
                          GameResultService gameResultService,
                          AccountStateService accountStateService) {
        this.betRepository = betRepository;
        this.gameResultService = gameResultService;
        this.accountStateService = accountStateService;
    }

    @Override
    public Bet createBet(Bet bet) {
        return betRepository.save(bet);
    }

    @Override
    public List<BetDto> findAllBetsByUserId(long userId) {
        List<Bet> bets = betRepository.findAllByUserId(userId);

        if (bets.isEmpty()) {
            throw new NoSuchEntityException(String.format("Could not find Bets by userId = %s", userId));
        }

        return bets.stream()
                .map(BetDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public GameResultDto placeBet(BetDto betDto, Long userId) {
        LOGGER.info(String.format("User with id = %s is placing the bet = %s", betDto.getUserId(), betDto));
        try (Unlockable lock = USER_LOCKS.lock(userId)) {
            AccountState accountState = accountStateService.findByUserId(userId);
            User user = (User) Hibernate.unproxy(accountState.getUser());

            BigDecimal betAmount = betDto.getBetAmount();
            BigDecimal balance = accountState.getBalance();

            validateBalance(balance);
            validateBetAmount(betAmount, balance);

            GameContext gameContext = createGameContext(balance, betAmount);

            GameResult gameResult = playGame(gameContext, user);

            BigDecimal balanceAfterGame = balance.add(gameResult.getWinAmount());
            accountState.setBalance(balanceAfterGame);
            accountStateService.updateAccountState(AccountStateDto.of(accountState));

            GameResultDto gameResultDto = GameResultDto.of(gameResult);

            LOGGER.info(String.format("User with id = %s got game result = %s", betDto.getUserId(), gameResultDto));

            return gameResultDto;
        }
    }

    private void validateBalance(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) < 1) {
            throw new BettingGameException("You cannot place a bet because your balance is empty");
        }
    }

    private void validateBetAmount(BigDecimal betAmount, BigDecimal balance) {
        if (betAmount.compareTo(balance) > 0) {
            throw new BettingGameException("You cannot place a bet because the amount of your bet is bigger than your balance");
        }
    }

    private GameContext createGameContext(BigDecimal balance, BigDecimal betAmount) {
        GameContext gameContext = new GameContext();
        gameContext.setBalance(balance);
        gameContext.setBetAmount(betAmount);
        gameContext.setFreeBet(false);
        return gameContext;
    }

    private GameResult playGame(GameContext gameContext, User user) {
        LOGGER.info(String.format("User with id = %s starts playing game with %s",
                user.getId(), gameContext));
        RoundResult roundResult = playRound(gameContext, user);

        BigDecimal winAmount = roundResult.getWinAmount();
        GameResult gameResult = new GameResult();
        gameResult.addBet(roundResult.getBet());

        while (roundResult.isGivenFreeBet()) {
            LOGGER.info(String.format("Since the user with id = %s won the free bet a new round will be played",
                    user.getId()));
            gameContext.setFreeBet(true);
            roundResult = playRound(gameContext, user);
            winAmount = winAmount.add(roundResult.getWinAmount());
            gameResult.addBet(roundResult.getBet());
        }

        gameResult.setWinAmount(winAmount);
        gameResult.setUser(user);

        return gameResultService.createGameResult(gameResult);
    }

    private RoundResult playRound(GameContext gameContext, User user) {
        LOGGER.info(String.format("Play Round started for userId = %s", user.getId()));
        RoundResult roundResult = playSlots(gameContext);

        Bet bet = new Bet();
        bet.setUser(user);
        bet.setBetAmount(gameContext.getBetAmount());
        bet.setWinAmount(roundResult.getWinAmount());
        bet.setFreeBet(gameContext.isFreeBet());
        bet.setCreatedDateTime(LocalDateTime.now());
        bet.setPlayedSlot(roundResult.getPlayedSlot());

        LOGGER.info(String.format("The bet was played with roundResult %s", roundResult));

        roundResult.setBet(bet);

        return roundResult;
    }

    private RoundResult playSlots(GameContext gameContext) {
        BigDecimal betAmount = gameContext.getBetAmount();
        boolean freeBet = gameContext.isFreeBet();
        BigDecimal winAmount = BigDecimal.ZERO;
        boolean givenFreeBet =  false;

        Slot playedSlot = playOutSlot();

        switch (playedSlot) {
            case WIN_TWENTY_EURO:
                winAmount = winAmount.add(BigDecimal.valueOf(20));
                break;
            case FREE_ROUND_AND_DOUBLE_MONEY_BACK:
                BigDecimal doubleBetAmount = betAmount.multiply(BigDecimal.valueOf(2));
                winAmount = winAmount.add(doubleBetAmount);
                givenFreeBet = true;
                break;
            case FREE_ROUND:
                if (!freeBet) {
                    winAmount = winAmount.subtract(betAmount);
                }
                givenFreeBet = true;
                break;
            case LOSE:
                if (!freeBet) {
                    winAmount = winAmount.subtract(betAmount);
                }
                break;
            default:
                break;
        }

        RoundResult roundResult = new RoundResult();
        roundResult.setWinAmount(winAmount);
        roundResult.setGivenFreeBet(givenFreeBet);
        roundResult.setPlayedSlot(playedSlot);
        return roundResult;
    }

    private Slot playOutSlot() {
        Roulette roulette = new Roulette();
        Random random = new Random();
        int rouletteNumber = random.nextInt(20);
        Slot slot = roulette.getSlots()[rouletteNumber];
        LOGGER.info(String.format("The slot = %s was played out", slot));
        return slot;
    }
}
