package com.ufersa.sistemaagendamento.model.dataStructures;

public interface IFila<T> {
    void add(T number); // fila => adiociona no fim

    T remove();

    T consultaInicio(); // primeiro elemento

    boolean isEmpty();

    boolean isFull();

    void show();
}
