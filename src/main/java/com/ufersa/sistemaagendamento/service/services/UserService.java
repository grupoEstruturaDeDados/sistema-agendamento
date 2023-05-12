package com.ufersa.sistemaagendamento.service.services;

import com.ufersa.sistemaagendamento.infrastructure.interfaces.IUserRepository;
import com.ufersa.sistemaagendamento.model.dataStructures.fila.IPilha;
import com.ufersa.sistemaagendamento.model.dataStructures.fila.PilhaLista;
import com.ufersa.sistemaagendamento.model.entities.User;
import com.ufersa.sistemaagendamento.model.requests.UserRequest;
import com.ufersa.sistemaagendamento.model.responses.UserResponse;
import com.ufersa.sistemaagendamento.service.interfaces.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    private IPilha<User> usersPilha;

    public UserService(IUserRepository userRepository){
        this.userRepository = userRepository;
        this.usersPilha = new PilhaLista<>();
    }

    @Override
    public UserResponse AddUser(UserRequest userRequest) {

        var registeredUser = GetUserByLoginData(userRequest.email, userRequest.senha);
        if(!registeredUser.isEmpty()){
            return new UserResponse(registeredUser.get().getAdmin(), "Bem-vindo de volta 🫡");
        }

        var isRegisteredUser = IsRegisteredUser(userRequest.email);
        if(isRegisteredUser){
            return new UserResponse(null, "Vish, senha incorreta ;/");
        }

        var user = new User();
        user.setEmail(userRequest.email);
        user.setSenha(userRequest.senha);
        user.setNome(userRequest.nome);
        user.setTelefone(userRequest.telefone);
        var response = userRepository.save(user);

        return new UserResponse(response.getAdmin(), "Usuário cadastrado 🖖");
    }

    @Override
    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> GetUserByLoginData(String email, String senha) {
        var allUsers = GetAllUsers();
        return allUsers.stream().filter(u -> u.getEmail().equals(email) && u.getSenha().equals(senha)).findFirst();
    }

    public Optional<User> GetUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Boolean IsRegisteredUser(String email) {
        var allUsers = GetAllUsers();
        var registeredUser = allUsers.stream().filter(u -> u.getEmail().equals(email)).findFirst();
        if(!registeredUser.isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public String UpdateUser(UserRequest userRequest) {
        var registeredUser = GetUserByLoginData(userRequest.email, userRequest.senha);
        if(registeredUser.isEmpty()){
            return "Usuário não cadastrado";
        }

        registeredUser.get().setNome(userRequest.nome);
        registeredUser.get().setTelefone(userRequest.telefone);
        registeredUser.get().setSenha(userRequest.senha);

        userRepository.save(registeredUser.get());

        usersPilha.empilhar(registeredUser.get());

        return "Feito!";
    }

    public String GetUserMaisRecente(){

        if(usersPilha.estaVazia()){
            return "Nenhum cliente :(";
        }

        var userMaisRecente = usersPilha.topo();
        return userMaisRecente.getId() + " - " + userMaisRecente.getNome() + " | " + userMaisRecente.getEmail() + " | " + userMaisRecente.getTelefone();
    }
}