package com.ufersa.sistemaagendamento.infrastructure.interfaces;

import com.ufersa.sistemaagendamento.model.entities.AvailableTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAvailableTimesRepository extends JpaRepository<AvailableTimes,Integer> {
}
