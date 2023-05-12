package com.ufersa.sistemaagendamento.service.services;

import com.ufersa.sistemaagendamento.infrastructure.interfaces.IAvailableTimesRepository;
import com.ufersa.sistemaagendamento.model.dataStructures.fila.ILista;
import com.ufersa.sistemaagendamento.model.dataStructures.fila.ListaEncadeada;
import com.ufersa.sistemaagendamento.model.entities.AvailableTimes;
import com.ufersa.sistemaagendamento.service.interfaces.IAvailableTimesService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class AvailableTimesService implements IAvailableTimesService {

    private IAvailableTimesRepository availableTimesRepository;

    private ILista<AvailableTimes> availableTimesList;

    private List<AvailableTimes> removedTimesList;

    public AvailableTimesService(IAvailableTimesRepository availableTimesRepository) {
        this.availableTimesRepository = availableTimesRepository;
        this.availableTimesList = new ListaEncadeada<AvailableTimes>();
        this.removedTimesList = new ArrayList<AvailableTimes>();
    }

    @Override
    public String AddAvailableTimes(String filePath) throws ParseException {

        var file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                var line = scanner.nextLine();
                var data = line.split(",");
                availableTimesList.addLast(new AvailableTimes(data[0], data[1]));
            }
        }
        catch (IOException e) {

        }
        finally {
            if(scanner != null) {
                scanner.close();
            }
        }

        for(var i : BubbleSort(availableTimesList)){
            availableTimesRepository.save(i);
        }

        return "Horários adicionados!";
    }

    @Override
    public List<AvailableTimes> GetAvailableTimes() {
        var allTimes = availableTimesRepository.findAll();
        var availables = new ArrayList<>(allTimes);
        for(var obj : allTimes){
            for(var objRemoved : removedTimesList) {
                if(obj.getId().equals(objRemoved.getId())){
                    availables.remove(obj);
                }
            }
        }

        return availables;
    }

    public void AddRemovedTimeAgain(String data, String hora) {
        var removedTime = removedTimesList.stream().filter(u -> u.getData().equals(data) && u.getHora().equals(hora)).findFirst();
        removedTimesList.remove(removedTime.get());
    }

    @Override
    public Boolean RemoveAvailableTime(Integer timeId) {
        var time = GetAvailableTimesById(timeId).get();
        removedTimesList.add(time);
        return true;
    }

    @Override
    public Optional<AvailableTimes> GetAvailableTimesById(Integer timeId) {
        var availableTimes = GetAvailableTimes();
        return availableTimes.stream().filter(u -> u.getId().equals(timeId)).findFirst();
    }

    private static ILista<AvailableTimes> BubbleSort(ILista<AvailableTimes> lista) throws ParseException {
        int size = lista.size() - 1;

        for(int i = 0; i < size; i++) {

            for(int j = 0; j < size; j++) {

                if(lista.get(j).getDateAndTime().after(lista.get(j+1).getDateAndTime())){
                    var temp = lista.get(j);
                    lista.set(j, lista.get(j+1));
                    lista.set(j+1, temp);
                }
            }
        }
        return lista;
    }
}
