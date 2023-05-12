package com.ufersa.sistemaagendamento.service.interfaces;

import com.ufersa.sistemaagendamento.model.dataStructures.fila.ILista;
import com.ufersa.sistemaagendamento.model.entities.AvailableTimes;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface IAvailableTimesService {

    String AddAvailableTimes(String filePath) throws ParseException;

    void AddRemovedTimeAgain(String data, String hora);

    List<AvailableTimes> GetAvailableTimes();

    Optional<AvailableTimes> GetAvailableTimesById(Integer timeId);

    Boolean RemoveAvailableTime(Integer timeId);

}
