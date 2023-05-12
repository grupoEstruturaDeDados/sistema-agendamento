package com.ufersa.sistemaagendamento.service.services;

import com.ufersa.sistemaagendamento.infrastructure.interfaces.IScheduleRepository;
import com.ufersa.sistemaagendamento.model.dataStructures.IFila;
import com.ufersa.sistemaagendamento.model.dataStructures.MinhaFilaVetor;
import com.ufersa.sistemaagendamento.model.entities.AvailableTimes;
import com.ufersa.sistemaagendamento.model.entities.Schedule;
import com.ufersa.sistemaagendamento.model.entities.User;
import com.ufersa.sistemaagendamento.model.requests.ScheduleRequest;
import com.ufersa.sistemaagendamento.service.interfaces.IAvailableTimesService;
import com.ufersa.sistemaagendamento.service.interfaces.IScheduleService;
import com.ufersa.sistemaagendamento.service.interfaces.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService implements IScheduleService {

    private IScheduleRepository scheduleRepository;
    private IUserService userService;
    private IAvailableTimesService availableTimesService;
    private IFila<User> queue;

    private List<AvailableTimes> listaHorarios;

    public ScheduleService(IScheduleRepository scheduleRepository,
        IUserService userService, IAvailableTimesService availableTimesService) {
        this.scheduleRepository = scheduleRepository;
        this.userService = userService;
        this.availableTimesService = availableTimesService;
        this.queue = new MinhaFilaVetor<>(5);
        this.listaHorarios = new ArrayList<>();
    }

    @Override
    public String AddSchedule(ScheduleRequest scheduleRequest) {

        var registeredUser = userService.GetUserByLoginData(scheduleRequest.email, scheduleRequest.senha);
        if(registeredUser.isEmpty()){
            return "Usuário não cadastrado";
        }

        var scheduleObj = new Schedule(registeredUser.get().getId(), scheduleRequest.timeId);
        scheduleRepository.save(scheduleObj);

        var timeObj = availableTimesService.GetAvailableTimesById(scheduleRequest.timeId);
        listaHorarios.add(timeObj.get());

        availableTimesService.RemoveAvailableTime(scheduleRequest.timeId);

        return "Agendamento bem sucedido!";
    }

    @Override
    public String AddIntoWaitingQueue(String email, String senha) {

        var registeredUser = userService.GetUserByLoginData(email, senha);
        if(registeredUser.isEmpty()){
            return "Usuário não cadastrado";
        }

        if(queue.isFull()){
            return "Desculpe, lista de espera cheia :(";
        }

        queue.add(registeredUser.get());

        return "Adicionado!";
    }

    @Override
    public String GetFirst() {

        if(queue.isEmpty()){
            return "Fila de espera vazia.";
        }

        var first = queue.consultaInicio();
        return "Nome: " + first.getNome() + "\n" + "Telefone: " + first.getTelefone();
    }

    public String GetAgendados() {
        var allAgendados = scheduleRepository.findAll();

        if(allAgendados.isEmpty()){
            return "Nenhum horário agendado.";
        }

        StringBuilder sb = new StringBuilder();

        var allAgendadosOrdenado = bubbleSort(allAgendados);

        for(var obj : allAgendadosOrdenado){
            var time = listaHorarios.stream().filter(u -> u.getId().equals(obj.getTimeId())).findFirst();
            var user = userService.GetUserById(obj.getUserId()).get();
            sb.append((allAgendadosOrdenado.indexOf(obj)+1) + " - " + user.getNome() + " | " + time.get().getData() + " às " + time.get().getHora() + "\n");
        }

        return sb.toString();
    }

    public String DeleteAgendamento(String email, String senha){

        var registeredUser = userService.GetUserByLoginData(email, senha);
        if(registeredUser.isEmpty()){
            return "Usuário não cadastrado";
        }

        var allAgendados = scheduleRepository.findAll();
        var obj = allAgendados.stream().filter(u -> u.getUserId().equals(registeredUser.get().getId())).findFirst();

        if(obj.isEmpty()){
            return "Agendamento não encontrado.";
        }

        scheduleRepository.delete(obj.get());
        var time = listaHorarios.stream().filter(u -> u.getId().equals(obj.get().getTimeId())).findFirst().get();
        availableTimesService.AddRemovedTimeAgain(time.getData(), time.getHora());
        return "Cancelado com sucesso.";
    }

    public String VerAgendamento(String email, String senha){
        var registeredUser = userService.GetUserByLoginData(email, senha);
        if(registeredUser.isEmpty()){
            return "Usuário não cadastrado";
        }

        var allAgendados = scheduleRepository.findAll();

        //var objAgendamento = allAgendados.stream().filter(u -> u.getUserId().equals(registeredUser.get().getId())).findFirst();

        var indexAgendamento = buscaBinaria(allAgendados, registeredUser.get().getId());

        if(indexAgendamento == -1){
            return "Agendamento não encontrado.";
        }

        var objAgendamento = allAgendados.get(indexAgendamento);


        var time = listaHorarios.stream().filter(u -> u.getId().equals(objAgendamento.getTimeId())).findFirst().get();

        return registeredUser.get().getNome() + " | " + time.getData() + " às " + time.getHora();
    }

    private static List<Schedule> bubbleSort(List<Schedule> lista) {
        int size = lista.size() - 1;

        for(int i = 0; i < size; i++) {

            for(int j = 0; j < size; j++) {

                if(lista.get(j).getTimeId() > lista.get(j+1).getTimeId()){
                    int tempId = lista.get(j).getId();
                    lista.get(j).setId(lista.get(j+1).getId());
                    lista.get(j+1).setId(tempId);

                    var temp = lista.get(j);
                    lista.set(j, lista.get(j+1));
                    lista.set(j+1, temp);
                }
            }
        }
        return lista;
    }

    public static int buscaBinaria(List<Schedule> lista, int chave) {
        int esquerda = 0;
        int direita = lista.size() - 1;

        while (esquerda <= direita) {
            int meio = (esquerda + direita) / 2;

            if (lista.get(meio).getId() == chave) {
                return meio;
            } else if (lista.get(meio).getId() < chave) {
                esquerda = meio + 1;
            } else {
                direita = meio - 1;
            }
        }

        return -1; // Retorna -1 se a chave não for encontrada na lista
    }
}