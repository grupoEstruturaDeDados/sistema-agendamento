package com.ufersa.sistemaagendamento.model.dataStructures.fila;

public class MinhaFilaVetor<T> implements IFila<T> {

    int size; // tamanho da fila
    // como e fila => precisamos saber do primeiro e ultimo
    int primeiro;
    int ultimo;

    private Object[] arrayFila;

    public MinhaFilaVetor(int tamanho) {
        this.primeiro = -1;
        this.ultimo = -1;
        this.size = tamanho;

        this.arrayFila = new Object[size];
    }

    public void add(T valor) {
        System.out.println("Inserir na fila...");

        int lastTemp = (ultimo + 1) % size;

        if (lastTemp == primeiro) {
            System.out.println("fila cheia");
        }

        ultimo = lastTemp;
        arrayFila[ultimo] = valor;

        // inserindo o primeiro
        if (primeiro == -1) {// vazio
            primeiro = 0;
        }
    }

    @SuppressWarnings("unchecked")
    public T remove() {
        System.out.println("removendo");

        T retorno;

        if (primeiro == -1) {
            System.out.println("fila vazia");
        }

        retorno = (T) arrayFila[primeiro];

        if (ultimo == primeiro) {
            ultimo = -1;
            primeiro = -1;
        } else {
            primeiro = (primeiro + 1) % size;
        }
        return retorno;
    }

    @SuppressWarnings("unchecked")
    public T consultaInicio() {
        System.out.println("consultando");

        T retorno;
        if (primeiro == -1) {
            System.out.println("fila vazia");
        }

        retorno = (T) arrayFila[primeiro];

        return retorno;
    }

    public boolean isEmpty() {
        if (primeiro == -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFull() {
        int lastTemp = (ultimo + 1) % size;
        if (lastTemp == primeiro) {
            return true;
        } else {
            return false;
        }
    }

    public void show() {
        int i = primeiro;

        if (primeiro == -1) {
            return;
        }

        while (i != ultimo) {
            System.out.println("posicao " + i + " = " + arrayFila[i] + "\n");

            i = (i + 1) % size;
        }
        System.out.println("posicao " + i + " = " + arrayFila[i] + "\n");

        System.out.println("inicio = " + primeiro + "  fim = " + ultimo + "\n");
    }

}