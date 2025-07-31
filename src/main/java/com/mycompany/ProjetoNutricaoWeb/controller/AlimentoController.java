package com.mycompany.ProjetoNutricaoWeb.controller;

import com.mycompany.ProjetoNutricaoWeb.model.AlimentoEntity;
import com.mycompany.ProjetoNutricaoWeb.model.UsuarioEntity;
import com.mycompany.ProjetoNutricaoWeb.service.AlimentoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.*;

@Controller
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    @GetMapping("/tabelaAlimentos")
    public String tabelaAlimentos(Model model, HttpSession session) {
        UsuarioEntity usuarioLogado = (UsuarioEntity) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            return "redirect:/login"; // se não estiver logado
        }

        model.addAttribute("usuario", usuarioLogado);
        return "tabelaAlimentos";
    }


    /*@GetMapping("/cadastroAlimentos")
    public String cadastroAlimentos(Model model, HttpSession session) {
        UsuarioEntity usuarioLogado = (UsuarioEntity) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            return "redirect:/login"; // se não estiver logado
        }

        model.addAttribute("usuario", usuarioLogado);
        return "cadastroAlimentos";
    }*/

    @GetMapping("/cadastroAlimentos")
    public String cadastroAlimentos(Model model) {
        AlimentoEntity alimento = new AlimentoEntity();
        model.addAttribute("alimento", alimento);
        return "cadastroAlimentos";
    }

    @PostMapping("/salvarAlimento")
    public String salvarAlimento(@ModelAttribute("alimento") AlimentoEntity alimento, BindingResult result, Model model) {
        if (alimento.getId() == null) {
            alimentoService.criarAlimento(alimento);
            return "redirect:/cadastroAlimentos";
        } else {
            alimentoService.atualizarAlimento(alimento.getId(), alimento);
        }
        return "redirect:/tabelaAlimentos";
    }
}
