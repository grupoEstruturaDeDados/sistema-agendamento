package com.ufersa.sistemaagendamento.model.dataStructures.fila;

import java.util.Iterator;

public interface ILista<T> extends Iterable<T> {
    int size();

    boolean isEmpty();

    void addFirst(T data);

    void addLast(T data);

    T removeFirst();

    boolean remove(T data);

    boolean contains(T data);

    void clear();

    T get(int index);

    void set(int index, T data);

    Iterator<T> iterator();
}
