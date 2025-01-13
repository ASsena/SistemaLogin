package com.sistemalogin.login;

import com.sistemalogin.login.domain.entities.Role;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);
		System.out.println(Role.Value.BASIC.name());
		System.out.println(Role.Value.BASIC.getValor());
	}

}
