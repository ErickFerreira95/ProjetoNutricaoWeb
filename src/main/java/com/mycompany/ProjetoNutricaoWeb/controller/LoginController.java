package com.mycompany.ProjetoNutricaoWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
    @GetMapping("/")
    public String getLoginView(){
    return "login";
    }

    @GetMapping("/cadastrarUsuario")
    public String getCadastrarUsuarioView(){
        return "cadastrarUsuario";
    }
}
