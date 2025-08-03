package com.mycompany.ProjetoNutricaoWeb.service;

import com.mycompany.ProjetoNutricaoWeb.data.AlimentoRepository;
import com.mycompany.ProjetoNutricaoWeb.model.AlimentoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlimentoService {

    @Autowired
    AlimentoRepository alimentoRepository;

    public AlimentoEntity criarAlimento(AlimentoEntity alimento) {
        alimento.setId(null);
        double kcal = (Double.parseDouble(alimento.getProteina()) * 4) + (Double.parseDouble(alimento.getCarboidrato()) * 4) + (Double.parseDouble(alimento.getGordura()) * 9);
        alimento.setKcal(String.valueOf(kcal));
        alimentoRepository.save(alimento);
        return alimento;
    }

    public AlimentoEntity atualizarAlimento(Integer alimentoId, AlimentoEntity alimentoEntity) {
        AlimentoEntity alimento = getAlimentoId(alimentoId);
        alimento.setNomeAlimento(alimentoEntity.getNomeAlimento());
        alimento.setQuantidade(alimentoEntity.getQuantidade());
        alimento.setProteina(alimentoEntity.getProteina());
        alimento.setCarboidrato(alimentoEntity.getCarboidrato());
        alimento.setGordura(alimentoEntity.getGordura());
        alimento.setKcal(alimentoEntity.getKcal());
        alimentoRepository.save(alimento);
        return alimento;
    }

    public AlimentoEntity getAlimentoId(Integer alimentoId) {
        return alimentoRepository.findById(alimentoId).orElse(null);
    }

    public List<AlimentoEntity> listarTodosAlimentos() {
        return alimentoRepository.findAll();
    }
 
    public void deletarAlimento(Integer alimentoId) {
        AlimentoEntity alimento = getAlimentoId(alimentoId);
        alimentoRepository.deleteById(alimento.getId());
    }
}
