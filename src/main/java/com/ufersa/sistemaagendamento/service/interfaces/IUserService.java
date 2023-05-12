package com.ufersa.sistemaagendamento.service.interfaces;

import com.ufersa.sistemaagendamento.model.entities.User;
import com.ufersa.sistemaagendamento.model.requests.UserRequest;
import com.ufersa.sistemaagendamento.model.responses.UserResponse;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    UserResponse AddUser(UserRequest userRequest);

    List<User> GetAllUsers();

    Optional<User> GetUserByLoginData(String email, String senha);

    Optional<User> GetUserById(int id);

    Boolean IsRegisteredUser(String email);

    String UpdateUser(UserRequest userRequest);

    String GetUserMaisRecente();
}
