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

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<AlimentoEntity> atualizarAlimento(@PathVariable Integer id, @RequestBody AlimentoEntity alimento) {
        var alimentoAtualizado = alimentoService.atualizarAlimento(id, alimento);
        return new ResponseEntity<>(alimentoAtualizado, HttpStatus.OK);
    }

    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<AlimentoEntity> getAlimentoById(@PathVariable Integer id) {
        AlimentoEntity alimento = alimentoService.getAlimentoId(id);
        return new ResponseEntity<>(alimento, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List> getAllAlimentos() {
        List<AlimentoEntity> alimentos = alimentoService.listarTodosAlimentos();
        return new ResponseEntity<>(alimentos, HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity deletarAlimento(@PathVariable Integer id) {
        alimentoService.deletarAlimento(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
