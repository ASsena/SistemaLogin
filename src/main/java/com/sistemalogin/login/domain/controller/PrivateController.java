package com.sistemalogin.login.domain.controller;

import com.sistemalogin.login.domain.entities.User;
import com.sistemalogin.login.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/view")
public class PrivateController {

    private final UserService user;

    public PrivateController(UserService user) {
        this.user = user;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> viewAllUsers(){
        return ResponseEntity.ok(user.allUsers());
    }
}
