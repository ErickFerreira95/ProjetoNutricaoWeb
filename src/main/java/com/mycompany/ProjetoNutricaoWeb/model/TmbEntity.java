package com.mycompany.ProjetoNutricaoWeb.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "taxaMetabolicaBasal")
public class TmbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Integer id;
    private String genero;
    private int idade;
    private int altura;
    private int peso;
    private Double fatorAtividade;
    private Double tmb;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioEntity usuario;

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public int getIdade() {
        return idade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Double getFatorAtividade() {
        return fatorAtividade;
    }

    public void setFatorAtividade(Double fatorAtividade) {
        this.fatorAtividade = fatorAtividade;
    }

    public Double getTmb() {
        return tmb;
    }

    public void setTmb(Double tmb) {
        this.tmb = tmb;
    }
}
