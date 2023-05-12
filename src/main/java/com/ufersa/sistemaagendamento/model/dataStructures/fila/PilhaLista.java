package com.ufersa.sistemaagendamento.model.dataStructures.fila;

import java.util.EmptyStackException;

public class PilhaLista<T> implements IPilha<T> {
    private class Node {
        private T elemento;
        private Node proximo;

        public Node(T elemento) {
            this.elemento = elemento;
        }
    }

    private Node topo;

    public PilhaLista() {
        topo = null;
    }

    public void empilhar(T elemento) {
        Node novoNode = new Node(elemento);
        novoNode.proximo = topo;
        topo = novoNode;
    }

    public T desempilhar() {
        if (estaVazia()) {
            throw new EmptyStackException();
        }

        T elementoDesempilhado = topo.elemento;
        topo = topo.proximo;
        return elementoDesempilhado;
    }

    public T topo() {
        if (estaVazia()) {
            throw new EmptyStackException();
        }

        return topo.elemento;
    }

    public boolean estaVazia() {
        return topo == null;
    }

    public int tamanho() {
        int tamanho = 0;
        Node noAtual = topo;
        while (noAtual != null) {
            tamanho++;
            noAtual = noAtual.proximo;
        }
        return tamanho;
    }
}