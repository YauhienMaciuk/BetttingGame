package com.betting.bettinggameapp.controller;

import com.betting.bettinggameapp.dto.BetDto;
import com.betting.bettinggameapp.dto.GameResultDto;
import com.betting.bettinggameapp.service.BetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BetController {

    private final BetService betService;

    public BetController(BetService betService) {
        this.betService = betService;
    }

    @Operation(summary = "To find all Bets by userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Bets by userId were found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema (schema = @Schema(implementation = BetDto.class)))}),
            @ApiResponse(responseCode = "404", description = "Could not find Bets by userId", content = @Content)
    })
    @GetMapping("/users/{userId}/bets")
    public List<BetDto> findAllByUserId(@PathVariable Long userId) {
        return betService.findAllBetsByUserId(userId);
    }

    @Operation(summary = "To place a Bet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Bet was placed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GameResultDto.class))}),
            @ApiResponse(responseCode = "400", description = "The request body contains invalid data", content = @Content)
    })
    @PostMapping("/bets")
    public GameResultDto placeBet(@Valid @RequestBody BetDto betDto) {
        return betService.placeBet(betDto);
    }
}
