package com.mycompany.ProjetoNutricaoWeb.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Alimentos")
public class AlimentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Integer id;
    private String nomeAlimento;
    private String quantidade;
    private String proteina;
    private String carboidrato;
    private String gordura;
    private String kcal;
    private double kcalNumero;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioEntity usuario;

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeAlimento() {
        return nomeAlimento;
    }

    public void setNomeAlimento(String nomeAlimento) {
        this.nomeAlimento = nomeAlimento;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getProteina() {
        return proteina;
    }

    public void setProteina(String proteina) {
        this.proteina = proteina;
    }

    public String getCarboidrato() {
        return carboidrato;
    }

    public void setCarboidrato(String carboidrato) {
        this.carboidrato = carboidrato;
    }

    public String getGordura() {
        return gordura;
    }

    public void setGordura(String gordura) {
        this.gordura = gordura;
    }

    public String getKcal() {
        kcalNumero = (Double.parseDouble(proteina) * 4) + (Double.parseDouble(carboidrato) * 4) + (Double.parseDouble(gordura) * 9);
        kcal = String.valueOf(kcalNumero);
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

    public double getKcalNumero() {
        return kcalNumero;
    }

    public void setKcalNumero(double kcalNumero) {
        this.kcalNumero = kcalNumero;
    }
}
