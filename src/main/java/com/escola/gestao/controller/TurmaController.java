package com.escola.gestao.controller;

import com.escola.gestao.model.Turma;
import com.escola.gestao.service.AlunoService;
import com.escola.gestao.service.AulaService;
import com.escola.gestao.service.DisciplinaService;
import com.escola.gestao.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/turmas")
public class TurmaController {

    private final TurmaService turmaService;
    private final AlunoService alunoService;
    private final AulaService aulaService;
    private final DisciplinaService disciplinaService;

    public TurmaController(TurmaService turmaService, AlunoService alunoService,
                           AulaService aulaService, DisciplinaService disciplinaService) {
        this.turmaService = turmaService;
        this.alunoService = alunoService;
        this.aulaService = aulaService;
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String pesquisa, Model model) {
        if (pesquisa != null && !pesquisa.isBlank()) {
            model.addAttribute("turmas", turmaService.pesquisarPorNome(pesquisa));
            model.addAttribute("pesquisa", pesquisa);
        } else {
            model.addAttribute("turmas", turmaService.listarTodas());
        }
        return "turmas/lista";
    }

    @GetMapping("/nova")
    public String novaForm(Model model) {
        model.addAttribute("turma", new Turma());
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("estados", Turma.EstadoTurma.values());
        return "turmas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Turma turma,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("disciplinas", disciplinaService.listarTodas());
            model.addAttribute("estados", Turma.EstadoTurma.values());
            return "turmas/formulario";
        }
        turmaService.guardar(turma);
        redirectAttributes.addFlashAttribute("mensagem", "Turma guardada com sucesso!");
        redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        return "redirect:/turmas";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return turmaService.buscarPorId(id).map(turma -> {
            model.addAttribute("turma", turma);
            model.addAttribute("disciplinas", disciplinaService.listarTodas());
            model.addAttribute("estados", Turma.EstadoTurma.values());
            return "turmas/formulario";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("mensagem", "Turma não encontrada!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            return "redirect:/turmas";
        });
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return turmaService.buscarPorId(id).map(turma -> {
            model.addAttribute("turma", turma);
            model.addAttribute("alunos", alunoService.listarPorTurma(id));
            model.addAttribute("aulas", aulaService.listarPorTurma(id));
            return "turmas/detalhes";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("mensagem", "Turma não encontrada!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            return "redirect:/turmas";
        });
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        turmaService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensagem", "Turma eliminada com sucesso!");
        redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        return "redirect:/turmas";
    }
}
