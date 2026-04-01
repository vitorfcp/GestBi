package com.escola.gestao.controller;

import com.escola.gestao.model.Aula;
import com.escola.gestao.service.AulaService;
import com.escola.gestao.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/aulas")
public class AulaController {

    private final AulaService aulaService;
    private final TurmaService turmaService;

    public AulaController(AulaService aulaService, TurmaService turmaService) {
        this.aulaService = aulaService;
        this.turmaService = turmaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("aulas", aulaService.listarTodas());
        return "aulas/lista";
    }

    @GetMapping("/nova")
    public String novaForm(@RequestParam(required = false) Long turmaId, Model model) {
        Aula aula = new Aula();
        if (turmaId != null) {
            turmaService.buscarPorId(turmaId).ifPresent(aula::setTurma);
        }
        model.addAttribute("aula", aula);
        model.addAttribute("turmas", turmaService.listarTodas());
        model.addAttribute("estados", Aula.EstadoAula.values());
        return "aulas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Aula aula,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("turmas", turmaService.listarTodas());
            model.addAttribute("estados", Aula.EstadoAula.values());
            return "aulas/formulario";
        }
        aulaService.guardar(aula);
        redirectAttributes.addFlashAttribute("mensagem", "Aula guardada com sucesso!");
        redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        return "redirect:/aulas";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return aulaService.buscarPorId(id).map(aula -> {
            model.addAttribute("aula", aula);
            model.addAttribute("turmas", turmaService.listarTodas());
            model.addAttribute("estados", Aula.EstadoAula.values());
            return "aulas/formulario";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("mensagem", "Aula não encontrada!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            return "redirect:/aulas";
        });
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return aulaService.buscarPorId(id).map(aula -> {
            model.addAttribute("aula", aula);
            return "aulas/detalhes";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("mensagem", "Aula não encontrada!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            return "redirect:/aulas";
        });
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        aulaService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensagem", "Aula eliminada com sucesso!");
        redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        return "redirect:/aulas";
    }
}
