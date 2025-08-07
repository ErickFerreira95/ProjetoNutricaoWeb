package com.mycompany.ProjetoNutricaoWeb.data;

import com.mycompany.ProjetoNutricaoWeb.model.TmbEntity;
import com.mycompany.ProjetoNutricaoWeb.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TmbRepository extends JpaRepository<TmbEntity, Integer> {
    Optional<TmbEntity> findTopByUsuarioOrderByIdDesc(UsuarioEntity usuario);
}
