package com.mycompany.ProjetoNutricaoWeb.data;

import com.mycompany.ProjetoNutricaoWeb.model.AlimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlimentoRepository extends JpaRepository<AlimentoEntity, Integer> {

    AlimentoEntity findByNomeAlimento(String nomeAlimento);

    List<AlimentoEntity> findByNomeAlimentoContaining(String nomeAlimento);

    List<AlimentoEntity> findByNomeAlimentoStartingWith(String nomeAlimento);

    List<AlimentoEntity> findByNomeAlimentoEndingWith(String nomeAlimento);

    List<AlimentoEntity> findByOrderByNomeAlimentoAsc();

    List<AlimentoEntity> findByOrderByNomeAlimentoDesc();
}
