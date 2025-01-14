package com.sistemalogin.login.domain.controller;

import com.sistemalogin.login.domain.entities.User;
import com.sistemalogin.login.domain.repository.UserRepository;
import com.sistemalogin.login.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/view")
public class PrivateController {

    private final UserService userService;

    public PrivateController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> viewAllUsers(){
        return ResponseEntity.ok(userService.allUsers());
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("unique/{username}")
    public ResponseEntity<User> viewUser(@PathVariable String username){
        try {
            return ResponseEntity.ok(userService.userEspecifico(username));
        }catch (ResponseStatusException e){
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("{username}")
    public ResponseEntity delUser(@PathVariable String username, JwtAuthenticationToken token){
        try{
            userService.deleteUser(username, token);
            return ResponseEntity.noContent().build();
        }catch (ResponseStatusException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para excluir esse usuário");
        }

    }
}
