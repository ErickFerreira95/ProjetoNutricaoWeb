package com.mycompany.ProjetoNutricaoWeb.controller;

import com.mycompany.ProjetoNutricaoWeb.data.AlimentoRepository;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    @Autowired
    private AlimentoRepository repository;

    @GetMapping("/tabelaAlimentos")
    public String tabelaAlimentos(Model model, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login"; // se não estiver logado
        }

        model.addAttribute("usuario", usuario);
        List<AlimentoEntity> alimentos = repository.findByUsuario(usuario);
        model.addAttribute("listarAlimentos", alimentos);
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

    /*@GetMapping("/cadastroAlimentos")
    public String cadastroAlimentos(Model model) {
        AlimentoEntity alimento = new AlimentoEntity();
        model.addAttribute("alimento", alimento);
        return "cadastroAlimentos";
    }*/

    @GetMapping("/cadastroAlimentos")
    public String mostrarFormulario(HttpSession session, Model model) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("alimento", new AlimentoEntity());
        session.removeAttribute("mensagem");
        return "cadastroAlimentos";
    }


    /*@PostMapping("/salvarAlimento")
    public String salvarAlimento(@ModelAttribute("alimento") AlimentoEntity alimento, BindingResult result, Model model) {
        if (alimento.getId() == null) {
            alimentoService.criarAlimento(alimento);
            return "redirect:/cadastroAlimentos";
        } else {
            alimentoService.atualizarAlimento(alimento.getId(), alimento);
        }
        return "redirect:/tabelaAlimentos";
    }*/

    @PostMapping("/salvarAlimento")
    public String salvarAlimento(@ModelAttribute("alimento") AlimentoEntity alimento,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogado");
        if (usuario == null) {
            return "redirect:/login";
        }

        alimento.setUsuario(usuario);
        repository.save(alimento);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Alimento cadastrado com sucesso!");
        return "redirect:/cadastroAlimentos";
    }

    @GetMapping("/editarAlimento/{id}")
    public String editarAlimento(@PathVariable(value = "id") Integer id, Model model) {
        AlimentoEntity alimento = alimentoService.getAlimentoId(id);
        model.addAttribute("alimento", alimento);
        return "editarAlimento";
    }

    @PostMapping("/salvarAlimentoEditado")
    public String salvarAlimentoEditado(@ModelAttribute("alimento") AlimentoEntity alimento, BindingResult result, Model model) {
        if (alimento.getId() == null) {
            alimentoService.criarAlimento(alimento);
            return "redirect:/cadastroAlimentos";
        } else {
            alimentoService.atualizarAlimento(alimento.getId(), alimento);
        }
        return "redirect:/tabelaAlimentos";
    }

    @GetMapping("/deletarAlimento/{id}")
    public String deletarAlimento(@PathVariable(value = "id") Integer id) {
        alimentoService.deletarAlimento(id);
        return "redirect:/tabelaAlimentos";
    }

    @GetMapping("/adicionarRefeicao")
    public String adicionarRefeicao(HttpSession session, Model model) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("alimento", new AlimentoEntity());
        return "adicionarRefeicao";
    }
}
