package com.sistemalogin.login.domain.service;

import com.sistemalogin.login.domain.dto.UserAccess;
import com.sistemalogin.login.domain.entities.User;
import com.sistemalogin.login.domain.repository.RoleRepository;
import com.sistemalogin.login.domain.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final UserRepository userepo;
    private final RoleRepository rolerepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    public TokenService(UserRepository userepo, RoleRepository rolerepo, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userepo = userepo;
        this.rolerepo = rolerepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    public String createToken(UserAccess userAccess) {

        var userrpository = userepo.findByUsername(userAccess.username());

        if (userrpository.isEmpty() || !userrpository.get().loginIsCorrect(userAccess, bCryptPasswordEncoder)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "username or password is invalid");
        }

        var now = Instant.now();
        var expir = 300L;

        var scopes = userrpository.get().getRoles()
                .stream()
                .map(role -> role.getName().toUpperCase())
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("login")
                .subject(userrpository.get().getUsername().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expir))
                .claim("scope", scopes)
                .build();

        return JwtEncoderParameters.from(claims).toString();
    }
}
