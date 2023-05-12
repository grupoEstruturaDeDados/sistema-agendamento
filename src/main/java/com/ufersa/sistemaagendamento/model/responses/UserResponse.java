package com.ufersa.sistemaagendamento.model.responses;

import com.ufersa.sistemaagendamento.model.entities.User;

public class UserResponse {

    public Boolean isAdmin;

    public String message;

    public UserResponse(){};

    public UserResponse(Boolean isAdmin, String message){
        this.isAdmin = isAdmin;
        this.message = message;
    }
}
