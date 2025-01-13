package com.sistemalogin.login.domain.controller;

import com.sistemalogin.login.domain.dto.InfoUser;
import com.sistemalogin.login.domain.dto.LoginResponse;
import com.sistemalogin.login.domain.dto.UserAccess;
import com.sistemalogin.login.domain.service.TokenService;
import com.sistemalogin.login.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class PublicController {

    private final UserService userService;
    private final TokenService tokenService;

    public PublicController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUserPost(@RequestBody InfoUser infoUser){
        try{
            userService.createUser(infoUser);
            return ResponseEntity.ok().build();
        }catch (ResponseStatusException e){
            return ResponseEntity.badRequest().body("user already exists");
        }

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserAccess userAccess){
        try{
            return ResponseEntity.ok(new LoginResponse(tokenService.createToken(userAccess), 300L));
        }catch (ResponseStatusException e){
            return ResponseEntity.badRequest().body("username or password is invalid");
        }

    }
}
