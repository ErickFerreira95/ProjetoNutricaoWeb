package com.mycompany.ProjetoNutricaoWeb.service;

import com.mycompany.ProjetoNutricaoWeb.data.AlimentoRepository;
import com.mycompany.ProjetoNutricaoWeb.model.AlimentoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlimentoService {

    @Autowired
    AlimentoRepository alimentoRepository;

    public AlimentoEntity criarAlimento(AlimentoEntity alimento) {
        alimento.setId(null);
        double kcal = (alimento.getProteina() * 4) + (alimento.getCarboidrato() * 4) + (alimento.getGordura() * 9);
        alimento.setKcal(kcal);
        alimentoRepository.save(alimento);
        return alimento;
    }
}
