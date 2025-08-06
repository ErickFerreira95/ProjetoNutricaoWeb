package com.mycompany.ProjetoNutricaoWeb.model;

import jakarta.persistence.*;
import lombok.Data;

import java.text.DecimalFormat;

@Data
@Entity
@Table(name = "Alimentos")
public class AlimentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Integer id;
    private String nomeAlimento;
    private Double quantidade;
    private Double proteina;
    private Double carboidrato;
    private Double gordura;
    private Double kcal;

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

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getProteina() {
        return proteina;
    }

    public void setProteina(Double proteina) {
        this.proteina = proteina;
    }

    public Double getCarboidrato() {
        return carboidrato;
    }

    public void setCarboidrato(Double carboidrato) {
        this.carboidrato = carboidrato;
    }

    public Double getGordura() {
        return gordura;
    }

    public void setGordura(Double gordura) {
        this.gordura = gordura;
    }

    public Double getKcal() {
        kcal = (proteina * 4) + (carboidrato * 4) + (gordura * 9);
        return kcal;
    }

    public void setKcal(Double kcal) {
        this.kcal = kcal;
    }
}
