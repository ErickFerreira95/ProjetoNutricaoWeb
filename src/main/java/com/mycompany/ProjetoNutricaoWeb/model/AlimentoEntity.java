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
    private double quantidade;
    private double proteina;
    private double carboidrato;
    private double gordura;
    private double kcal;

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

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getProteina() {
        return proteina;
    }

    public void setProteina(double proteina) {
        this.proteina = proteina;
    }

    public double getCarboidrato() {
        return carboidrato;
    }

    public void setCarboidrato(double carboidrato) {
        this.carboidrato = carboidrato;
    }

    public double getGordura() {
        return gordura;
    }

    public void setGordura(double gordura) {
        this.gordura = gordura;
    }

    public double getKcal() {
        kcal = (proteina * 4) + (carboidrato * 4) + (gordura * 9);
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }
}
