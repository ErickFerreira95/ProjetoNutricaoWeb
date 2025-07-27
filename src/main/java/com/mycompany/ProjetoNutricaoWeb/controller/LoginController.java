package com.mycompany.ProjetoNutricaoWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
    @GetMapping("/")
    public String ola(){
    return "login";
    }
}
