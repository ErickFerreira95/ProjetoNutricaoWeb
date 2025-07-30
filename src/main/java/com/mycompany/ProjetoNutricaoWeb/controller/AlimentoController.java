package com.mycompany.ProjetoNutricaoWeb.controller;

import com.mycompany.ProjetoNutricaoWeb.model.AlimentoEntity;
import com.mycompany.ProjetoNutricaoWeb.service.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    @GetMapping("/tabelaAlimentos")
    public String tabelaAlimentos(Model model) {
        AlimentoEntity alimento = new AlimentoEntity();
        model.addAttribute("alimento", alimento);
        return "tabelaAlimentos";
    }
}
