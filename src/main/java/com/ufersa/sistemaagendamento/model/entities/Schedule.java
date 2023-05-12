package com.ufersa.sistemaagendamento.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "agendamento")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer timeId;

    public Schedule(){};

    public Schedule (Integer userId, Integer timeId) {
        this.userId = userId;
        this.timeId = timeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }
}