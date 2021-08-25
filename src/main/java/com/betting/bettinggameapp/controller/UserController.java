package com.betting.bettinggameapp.controller;

import com.betting.bettinggameapp.dto.UserDto;
import com.betting.bettinggameapp.entity.User;
import com.betting.bettinggameapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "To find a User by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The User was found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Could not find the User by id", content = @Content)
    })
    @GetMapping("/{id}")
    public User findById(@NotBlank @PathVariable Long id) {
        return userService.findById(String.valueOf(id));
    }

    @Operation(summary = "To create a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The User was created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "The request body contains invalid data", content = @Content)
    })
    @PostMapping
    public User createUser(@RequestBody @Valid UserDto userDto) {
        return userService.createUser(userDto);
    }
}
