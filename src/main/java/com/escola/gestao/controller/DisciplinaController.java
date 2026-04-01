package com.escola.gestao.controller;

import com.escola.gestao.model.Disciplina;
import com.escola.gestao.service.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        return "disciplinas/lista";
    }

    @GetMapping("/nova")
    public String novaForm(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "disciplinas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Disciplina disciplina,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "disciplinas/formulario";
        }
        disciplinaService.guardar(disciplina);
        redirectAttributes.addFlashAttribute("mensagem", "Disciplina guardada com sucesso!");
        redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        return "redirect:/disciplinas";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return disciplinaService.buscarPorId(id).map(disciplina -> {
            model.addAttribute("disciplina", disciplina);
            return "disciplinas/formulario";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("mensagem", "Disciplina não encontrada!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            return "redirect:/disciplinas";
        });
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        disciplinaService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensagem", "Disciplina eliminada com sucesso!");
        redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        return "redirect:/disciplinas";
    }
}
