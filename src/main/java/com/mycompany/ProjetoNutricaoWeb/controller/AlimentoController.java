package com.mycompany.ProjetoNutricaoWeb.controller;

import com.mycompany.ProjetoNutricaoWeb.data.AlimentoRepository;
import com.mycompany.ProjetoNutricaoWeb.data.RefeicaoRepository;
import com.mycompany.ProjetoNutricaoWeb.model.AlimentoEntity;
import com.mycompany.ProjetoNutricaoWeb.model.RefeicaoEntity;
import com.mycompany.ProjetoNutricaoWeb.model.UsuarioEntity;
import com.mycompany.ProjetoNutricaoWeb.service.AlimentoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    @Autowired
    private AlimentoRepository repository;

    @Autowired
    private RefeicaoRepository refeicaoRepository;

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

        // Calcula a kcal dinamicamente
        Double proteina= alimento.getProteina() != null ? alimento.getProteina() : 0.0;
        Double carboidrato = alimento.getCarboidrato() != null ? alimento.getCarboidrato() : 0.0;
        Double gordura = alimento.getGordura() != null ? alimento.getGordura() : 0.0;

        Double kcalCalculada = (proteina * 4) + (carboidrato * 4) + (gordura * 9);
        alimento.setKcal(kcalCalculada);

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

    @GetMapping("/buscarAlimento")
    public String buscarAlimento(@RequestParam("nomeAlimento") String nomeAlimento,
                                 @RequestParam("quantidade") double quantidadeInformada,
                                 Model model, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogado");
        Optional<AlimentoEntity> alimentoOptional = alimentoService.buscarAlimentoPorNome(nomeAlimento, usuario);

        if (alimentoOptional.isPresent()) {
            AlimentoEntity alimento = alimentoOptional.get();

            double fator = quantidadeInformada / alimento.getQuantidade();

            double proteina = alimento.getProteina() * fator;
            double carboidrato = alimento.getCarboidrato() * fator;
            double gordura = alimento.getGordura() * fator;
            double kcal = alimento.getKcal() * fator;

            model.addAttribute("alimento", alimento);
            model.addAttribute("quantidadeInformada", quantidadeInformada);
            model.addAttribute("proteina", Math.round(proteina * 10.0) / 10.0);
            model.addAttribute("carboidrato", Math.round(carboidrato * 10.0) / 10.0);
            model.addAttribute("gordura", Math.round(gordura * 10.0) / 10.0);
            model.addAttribute("kcal", Math.round(kcal * 10.0) / 10.0);

        } else {
            model.addAttribute("erro", "Alimento não encontrado.");
            model.addAttribute("alimento", new AlimentoEntity());
        }
        return "adicionarRefeicao";
    }

    @PostMapping("/adicionarAlimentoRefeicao")
    public String adicionarAlimentoRefeicao(
            @RequestParam("nomeAlimento") String nomeAlimento,
            @RequestParam("quantidade") double quantidade,
            @RequestParam("proteina") double proteina,
            @RequestParam("carboidrato") double carboidrato,
            @RequestParam("gordura") double gordura,
            @RequestParam("kcal") double kcal,
            @RequestParam("refeicao") String refeicao,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogado");
        if (usuario == null) {
            return "redirect:/login";
        }

        RefeicaoEntity novoItem = new RefeicaoEntity();
        novoItem.setNomeAlimento(nomeAlimento);
        novoItem.setQuantidade(quantidade);
        novoItem.setProteina(proteina);
        novoItem.setCarboidrato(carboidrato);
        novoItem.setGordura(gordura);
        novoItem.setKcal(kcal);
        novoItem.setRefeicao(refeicao);
        novoItem.setUsuario(usuario);

        alimentoService.salvarRefeicao(novoItem);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Alimento adicionado à refeição!");

        return "redirect:/adicionarRefeicao";
    }

    @GetMapping("/refeicoes")
    public String mostrarTodasAsRefeicoes(Model model, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogado");
        if (usuario == null) {
            return "redirect:/login";
        }

        Map<String, List<RefeicaoEntity>> refeicoesMap = new LinkedHashMap<>();

        for (int i = 1; i <= 6; i++) {
            String nomeRefeicao = "Refeição " + i;
            List<RefeicaoEntity> alimentos = refeicaoRepository.findByRefeicaoAndUsuario(nomeRefeicao, usuario);
            refeicoesMap.put(nomeRefeicao, alimentos);
        }

        model.addAttribute("refeicoesMap", refeicoesMap);
        return "refeicoes";
    }

    @GetMapping("/deletarAlimentoRefeicao/{id}")
    public String deletarAlimentoRefeicao(@PathVariable(value = "id") Integer id) {
        alimentoService.deletarAlimentoRefeiecao(id);
        return "redirect:/refeicoes";
    }
}
