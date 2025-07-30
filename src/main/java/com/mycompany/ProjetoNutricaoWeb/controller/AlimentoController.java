package com.mycompany.ProjetoNutricaoWeb.controller;

import com.mycompany.ProjetoNutricaoWeb.model.AlimentoEntity;
import com.mycompany.ProjetoNutricaoWeb.model.UsuarioEntity;
import com.mycompany.ProjetoNutricaoWeb.service.AlimentoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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


    @GetMapping("/cadastroAlimentos")
    public String cadastroAlimentos(Model model, HttpSession session) {
        UsuarioEntity usuarioLogado = (UsuarioEntity) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            return "redirect:/login"; // se não estiver logado
        }

        model.addAttribute("usuario", usuarioLogado);
        return "cadastroAlimentos";
    }
}
