package com.mycompany.ProjetoNutricaoWeb.data;

import com.mycompany.ProjetoNutricaoWeb.model.TmbEntity;
import com.mycompany.ProjetoNutricaoWeb.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TmbRepository extends JpaRepository<TmbEntity, Integer> {
}
