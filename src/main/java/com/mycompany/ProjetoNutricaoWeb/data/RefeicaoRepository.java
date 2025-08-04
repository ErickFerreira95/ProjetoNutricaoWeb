package com.mycompany.ProjetoNutricaoWeb.data;

import com.mycompany.ProjetoNutricaoWeb.model.RefeicaoEntity;
import com.mycompany.ProjetoNutricaoWeb.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefeicaoRepository extends JpaRepository<RefeicaoEntity, Integer> {
    List<RefeicaoEntity> findByUsuario(UsuarioEntity usuario);
}
