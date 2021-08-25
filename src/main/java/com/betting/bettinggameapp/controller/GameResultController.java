package com.betting.bettinggameapp.controller;

import com.betting.bettinggameapp.dto.GameResultDto;
import com.betting.bettinggameapp.service.GameResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameResultController {

    private final GameResultService gameResultService;

    public GameResultController(GameResultService gameResultService) {
        this.gameResultService = gameResultService;
    }

    @Operation(summary = "To find all GameResults by userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All GameResults by userId were found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GameResultDto.class)))}),
            @ApiResponse(responseCode = "404", description = "Could not find GameResults by userId", content = @Content)
    })
    @GetMapping("/users/{userId}/game-results")
    public List<GameResultDto> findAllByUserId(@PathVariable Long userId) {
        return gameResultService.findAllGameResultDtosByUserId(userId);
    }
}
