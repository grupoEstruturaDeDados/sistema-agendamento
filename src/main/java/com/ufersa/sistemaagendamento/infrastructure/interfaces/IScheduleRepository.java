package com.ufersa.sistemaagendamento.infrastructure.interfaces;

import com.ufersa.sistemaagendamento.model.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IScheduleRepository extends JpaRepository<Schedule,Integer> {
}
