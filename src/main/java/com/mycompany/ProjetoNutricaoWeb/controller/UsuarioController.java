package com.mycompany.ProjetoNutricaoWeb.controller;

import com.mycompany.ProjetoNutricaoWeb.UsuarioService;
import com.mycompany.ProjetoNutricaoWeb.model.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    
    @GetMapping("/login")
    public String getLoginView(Model model){
        model.addAttribute("usuario", new UsuarioEntity());
    return "login";
    }

    @GetMapping("/criarUsuario")
    public String criarUsuario(Model model) {
        UsuarioEntity usuario = new UsuarioEntity();
        model.addAttribute("usuario", usuario);
        return "cadastrarUsuario";
    }

    @PostMapping("/salvarUsuario")
    public String salvarUsuario(@ModelAttribute("usuario") UsuarioEntity usuario) {
        if (usuario.getId() == null) {
            usuarioService.criarUsuario(usuario);
        }
        return "redirect:/login";
    }

    @PostMapping("/fazerLogin")
    public String fazerLogin (@ModelAttribute("usuario") UsuarioEntity usuario, Model model) {
        boolean autenticado = usuarioService.validarLogin(usuario.getEmail(), usuario.getSenha());

        if (autenticado) {
            return "cadastrarUsuario";
        } else {
            model.addAttribute("erro", "Email ou senha inválida!");
            return "login";
        }
    }
}
