package com.ufersa.sistemaagendamento.model.dataStructures.fila;

public interface IPilha<T> {
    void empilhar(T elemento);
    T desempilhar();
    T topo();
    boolean estaVazia();
    int tamanho();
}

