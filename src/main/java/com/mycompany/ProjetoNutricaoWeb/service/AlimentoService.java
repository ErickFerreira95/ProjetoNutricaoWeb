package com.mycompany.ProjetoNutricaoWeb.service;

import com.mycompany.ProjetoNutricaoWeb.data.AlimentoRepository;
import com.mycompany.ProjetoNutricaoWeb.data.RefeicaoRepository;
import com.mycompany.ProjetoNutricaoWeb.model.AlimentoEntity;
import com.mycompany.ProjetoNutricaoWeb.model.RefeicaoEntity;
import com.mycompany.ProjetoNutricaoWeb.model.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlimentoService {

    @Autowired
    AlimentoRepository alimentoRepository;

    @Autowired
    RefeicaoRepository refeicaoRepository;

    public AlimentoEntity criarAlimento(AlimentoEntity alimento) {
        alimento.setId(null);
        double kcal = (alimento.getProteina() * 4) + (alimento.getCarboidrato() * 4) + (alimento.getGordura() * 9);
        alimento.setKcal(kcal);
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

    public RefeicaoEntity getAlimentoRefeicaoId(Integer alimentoId) {
        return refeicaoRepository.findById(alimentoId).orElse(null);
    }

    public List<AlimentoEntity> listarTodosAlimentos() {
        return alimentoRepository.findAll();
    }

    public Optional<AlimentoEntity> buscarAlimentoPorNome(String nomeAlimento, UsuarioEntity usuario) {
        return alimentoRepository.findByNomeAlimentoAndUsuario(nomeAlimento, usuario);
    }

    public RefeicaoEntity salvarRefeicao(RefeicaoEntity refeicao) {
        return refeicaoRepository.save(refeicao);
    }
 
    public void deletarAlimento(Integer alimentoId) {
        AlimentoEntity alimento = getAlimentoId(alimentoId);
        alimentoRepository.deleteById(alimento.getId());
    }

    public void deletarAlimentoRefeiecao(Integer alimentoId) {
        RefeicaoEntity alimento = getAlimentoRefeicaoId(alimentoId);
        refeicaoRepository.deleteById(alimento.getId());
    }
}
