package com.ufersa.sistemaagendamento.service.interfaces;

import com.ufersa.sistemaagendamento.model.entities.User;
import com.ufersa.sistemaagendamento.model.requests.ScheduleRequest;

public interface IScheduleService {

    String AddSchedule(ScheduleRequest scheduleRequest);

    String AddIntoWaitingQueue(String email, String senha);

    String GetFirst();

    String GetAgendados();

    String DeleteAgendamento(String email, String senha);

    String VerAgendamento(String email, String senha);

}
