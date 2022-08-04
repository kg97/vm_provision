package com.vm.controller;

import com.vm.entity.VMUser;
import com.vm.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.vm.service.UserService;
import com.vm.util.JwtTokenUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Rest controller for user account related endpoints
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * API for VMUser signup
     */
    @PostMapping("/VMUser-signup")
    public ApiResponse signupUser(@Valid @RequestBody VMUser vmUser) {
        try {
            userService.signupUser(vmUser);
            return new ApiResponse("SUCCESS",HttpStatus.CREATED);
        } catch (Exception e){
            return new ApiResponse(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API for deleting user(soft delete)
     */
    @PostMapping("/delete-user")
    public ApiResponse deleteUser(@NotNull(message = "MISSING_TOKEN") @RequestHeader("Authorization") String token, @RequestParam("emailAddress") String requestedEmail) {
        try {
            String emailAddress = jwtTokenUtil.getUsernameFromToken(token);
            userService.deleteUser(emailAddress, requestedEmail);
            return new ApiResponse("SUCCESS",HttpStatus.CREATED);
        } catch (Exception e){
            return new ApiResponse(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
