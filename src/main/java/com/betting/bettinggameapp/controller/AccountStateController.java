package com.betting.bettinggameapp.controller;

import com.betting.bettinggameapp.dto.AccountStateDto;
import com.betting.bettinggameapp.service.AccountStateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountStateController {

    private final AccountStateService accountStateService;

    public AccountStateController(AccountStateService accountStateService) {
        this.accountStateService = accountStateService;
    }

    @Operation(summary = "To find a AccountState by userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The AccountState by userId was found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountStateDto.class))}),
            @ApiResponse(responseCode = "404", description = "Could not find the AccountState by userId", content = @Content)
    })
    @GetMapping("/user/{userId}/account-state")
    public AccountStateDto findAccountState(@PathVariable Long userId) {
        return accountStateService.findAccountStateDtoByUserId(userId);
    }
}