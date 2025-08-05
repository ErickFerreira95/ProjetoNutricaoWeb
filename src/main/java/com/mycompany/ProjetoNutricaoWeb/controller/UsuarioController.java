package com.mycompany.ProjetoNutricaoWeb.controller;

import com.mycompany.ProjetoNutricaoWeb.model.TmbEntity;
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
    public String salvarUsuario(@ModelAttribute("usuario") UsuarioEntity usuario, Model model) {
        try {
            usuarioService.criarUsuario(usuario);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastrarUsuario";
        }
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
            return "redirect:/login";
        } else {
            model.addAttribute("erro", "E-mail não encontrado.");
            return "redefinirSenha";
        }
    }

    @GetMapping("/criarTmb")
    public String criarTmb(Model model) {
        UsuarioEntity usuario = new UsuarioEntity();
        model.addAttribute("usuario", usuario);
        return "calculoTmb";
    }

    @PostMapping("/calcularTmb")
    public String calcularTmb(@ModelAttribute("usuario") TmbEntity tmbEntity, Model model, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogado");
        tmbEntity.setUsuario(usuario);
        double tmb;

        if ("masculino".equalsIgnoreCase(tmbEntity.getGenero())) {
            tmb = 66.47 + (13.75 * tmbEntity.getPeso()) + (5.00 * tmbEntity.getAltura()) - (6.76 * tmbEntity.getIdade());
        } else {
            tmb = 655.1 + (9.56 * tmbEntity.getPeso()) + (1.85 * tmbEntity.getAltura()) - (4.68 * tmbEntity.getIdade());
        }

        double gastoTotal = tmb * tmbEntity.getFatorAtividade();

        tmbEntity.setTmb(gastoTotal);
        usuarioService.salvarTmb(tmbEntity);
        String mensagem = String.format("Seu TMB é: %.1f kcal", gastoTotal);
        model.addAttribute("mensagemSucesso", mensagem);
        return "calculoTmb";
    }
}
