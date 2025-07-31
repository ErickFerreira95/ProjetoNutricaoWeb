package com.mycompany.ProjetoNutricaoWeb.controller;

import com.mycompany.ProjetoNutricaoWeb.service.UsuarioService;
import com.mycompany.ProjetoNutricaoWeb.model.UsuarioEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AlimentoController alimentoController;
    
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
        return "login";
    }

    @PostMapping("/fazerLogin")
    public String fazerLogin(@ModelAttribute("usuario") UsuarioEntity usuario, Model model, HttpSession session) {
        UsuarioEntity usuarioAutenticado = usuarioService.validarLogin(usuario.getEmail(), usuario.getSenha());

        if (usuarioAutenticado != null) {
            session.setAttribute("usuarioLogado", usuarioAutenticado);

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    usuarioAutenticado.getEmail(),
                    usuarioAutenticado.getSenha(),
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );

            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // REGISTRA NO CONTEXTO E NA SESSÃO
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

            return "redirect:/tabelaAlimentos";
        } else {
            model.addAttribute("erro", "Email ou senha inválidos");
            return "login";
        }
    }

    @GetMapping("/redefinirSenha")
    public String redefinirSenha(Model model) {
        model.addAttribute("usuario", new UsuarioEntity());
        return "redefinirSenha";
    }

    @PostMapping("/salvarNovaSenha")
    public String salvarNovaSenha(@ModelAttribute("usuario") UsuarioEntity usuario, Model model) {
        boolean atualizado = usuarioService.atualizarSenhaPorEmail(usuario.getEmail(), usuario.getSenha());

        if (atualizado) {
            return "login";
        } else {
            model.addAttribute("erro", "E-mail não encontrado.");
            return "redefinirSenha";
        }
    }
}
