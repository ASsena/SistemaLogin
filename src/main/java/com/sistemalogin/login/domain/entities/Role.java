package com.sistemalogin.login.domain.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum Value{
        ADMIN(1L),
        BASIC(2L);

        private Long valor;

        Value(Long valor) {
            this.valor = valor;
        }

        public Long getValor() {
            return valor;
        }

        public void setValor(Long valor) {
            this.valor = valor;
        }
    }
}
