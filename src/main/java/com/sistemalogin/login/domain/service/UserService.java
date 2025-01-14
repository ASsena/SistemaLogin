package com.sistemalogin.login.domain.service;

import com.sistemalogin.login.domain.dto.InfoUser;
import com.sistemalogin.login.domain.entities.Role;
import com.sistemalogin.login.domain.entities.User;
import com.sistemalogin.login.domain.repository.RoleRepository;
import com.sistemalogin.login.domain.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void createUser(InfoUser infoUser){

        var roles = roleRepository.findByName(Role.Value.BASIC.name());

       userRepository.findByUsername(infoUser.username()).ifPresentOrElse(
            users -> {throw new ResponseStatusException(HttpStatus.FORBIDDEN);},
            () -> {
                var user = new User();
                user.setUsername(infoUser.username());
                user.setPassword(passwordEncoder.encode(infoUser.password()));
                user.setRoles(Set.of(roles));
                userRepository.save(user);
            }
        );
    }

    public User userEspecifico(String username){
        var userep = userRepository.findByUsername(username);
        if(userep.isPresent() && userep.get().isAtivo()){
            return userep.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<User> allUsers(){
        return userRepository.findAllByAtivoTrue();
    }

    public void deleteUser(String username, JwtAuthenticationToken token){

        var user = userRepository.findByUsername(username);

        if(token.getTokenAttributes().get("scope").equals("ADMIN")){
            user.get().setAtivo(false);
            userRepository.save(user.get());
        } else if(user.get().getUsername().equals(token.getName())){
            user.get().setAtivo(false);
            token.setAuthenticated(false);
            userRepository.save(user.get());
        }else{
           throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

}
