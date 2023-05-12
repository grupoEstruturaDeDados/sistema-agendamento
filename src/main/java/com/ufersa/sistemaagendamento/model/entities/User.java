package com.ufersa.sistemaagendamento.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class User {

    private static final String SENHA_ADMIN = "senhasecreta";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = true)
    private String nome;

    @Column(nullable = true)
    private String telefone;

    @Column(nullable = false)
    private Boolean admin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;

        if(senha.equals(SENHA_ADMIN)){
            setAdmin(true);
        }
        else {
            setAdmin(false);
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}