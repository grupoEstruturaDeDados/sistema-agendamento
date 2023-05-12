package com.ufersa.sistemaagendamento.controller;

import com.ufersa.sistemaagendamento.model.entities.AvailableTimes;
import com.ufersa.sistemaagendamento.model.requests.ScheduleRequest;
import com.ufersa.sistemaagendamento.model.requests.UserRequest;
import com.ufersa.sistemaagendamento.service.interfaces.IAvailableTimesService;
import com.ufersa.sistemaagendamento.service.interfaces.IScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private IAvailableTimesService availableTimesService;
    private IScheduleService scheduleService;

    public ScheduleController(IAvailableTimesService availableTimesService, IScheduleService scheduleService){
        this.availableTimesService = availableTimesService;
        this.scheduleService = scheduleService;
    }

    @PostMapping("/horarios/gerar")
    public ResponseEntity<String> AddAvailableTimes(@RequestHeader String filePath) throws ParseException {
        var response = availableTimesService.AddAvailableTimes(filePath);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/horarios/disponiveis")
    public ResponseEntity<List<AvailableTimes>> GetAvailableTimes() throws ParseException {
        var response = availableTimesService.GetAvailableTimes();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/agendar")
    public ResponseEntity<String> AddSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        var response = scheduleService.AddSchedule(scheduleRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/agendados")
    public ResponseEntity<String> GetAgendados() {
        var response = scheduleService.GetAgendados();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/agendados/meuagendamento")
    public ResponseEntity<String> VerAgendamento(@RequestHeader String email, @RequestHeader String senha) {
        var response = scheduleService.VerAgendamento(email, senha);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/agendados/cancelar")
    public ResponseEntity<String> DeleteAgendamento(@RequestHeader String email, @RequestHeader String senha) {
        var response = scheduleService.DeleteAgendamento(email, senha);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/filaespera")
    public ResponseEntity<String> AddIntoWaitingQueue(@RequestBody UserRequest userRequest) {
        var response = scheduleService.AddIntoWaitingQueue(userRequest.email, userRequest.senha);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/filaespera")
    public ResponseEntity<String> GetFirst() {
        var response = scheduleService.GetFirst();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
