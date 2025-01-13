package com.sistemalogin.login.domain.controller;

import com.sistemalogin.login.domain.dto.InfoUser;
import com.sistemalogin.login.domain.dto.LoginResponse;
import com.sistemalogin.login.domain.dto.UserAccess;
import com.sistemalogin.login.domain.service.TokenService;
import com.sistemalogin.login.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        userService.createUser(infoUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserAccess userAccess){
        return ResponseEntity.ok(new LoginResponse(tokenService.createToken(userAccess), 300L));
    }
}
