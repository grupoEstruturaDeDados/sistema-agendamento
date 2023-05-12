package com.ufersa.sistemaagendamento.infrastructure.interfaces;

import com.ufersa.sistemaagendamento.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Integer> {
}
