package com.sistemalogin.login.domain.config;

import com.sistemalogin.login.domain.entities.Role;
import com.sistemalogin.login.domain.entities.User;
import com.sistemalogin.login.domain.repository.RoleRepository;
import com.sistemalogin.login.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Configuration
public class AdminConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminConfig(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        var roles = roleRepository.findByName(Role.Value.ADMIN.name());
        userRepository.findByUsername("admin").ifPresentOrElse(
                users -> System.out.println("admin já existe"),
                () -> {
                    var user = new User();
                    user.setUsername("admin");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.setRoles(Set.of(roles));
                    userRepository.save(user);
                }
        );
    }
}
