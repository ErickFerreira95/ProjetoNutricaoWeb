package com.mycompany.ProjetoNutricaoWeb.controller;

import com.mycompany.ProjetoNutricaoWeb.model.AlimentoEntity;
import com.mycompany.ProjetoNutricaoWeb.service.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alimento")
public class AlimentoControllerRest {
    @Autowired

    AlimentoService alimentoService;

    @PostMapping("/adicionar")
    public ResponseEntity<AlimentoEntity> addAlimento(@RequestBody AlimentoEntity alimento) {
        var novoAlimento = alimentoService.criarAlimento(alimento);
        return new ResponseEntity<>(novoAlimento, HttpStatus.CREATED);
    }
}
