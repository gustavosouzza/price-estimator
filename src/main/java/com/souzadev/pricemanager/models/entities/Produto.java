package com.souzadev.pricemanager.models.entities;

import jakarta.persistence.*;

@Entity
public class Produto {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true)
    private String nome;

    private double custo;
    private int quantidade;
    private double margem;
    private double precoVenda;

    public Produto() {
    }

    public Produto(String nome, double custo, int quantidade, double margem) {
        this.nome = nome;
        this.custo = custo;
        this.quantidade = quantidade;
        this.margem = margem;
        calcularPrecoVenda();
    }

    public void calcularPrecoVenda() {
        this.precoVenda = this.custo + (this.custo * this.margem / 100);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public double getMargem() {
        return margem;
    }

    public void setMargem(double margem) {
        this.margem = margem;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }
}
