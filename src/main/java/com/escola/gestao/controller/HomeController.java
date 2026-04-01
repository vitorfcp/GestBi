package com.escola.gestao.controller;

import com.escola.gestao.service.AlunoService;
import com.escola.gestao.service.AulaService;
import com.escola.gestao.service.DisciplinaService;
import com.escola.gestao.service.TurmaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AlunoService alunoService;
    private final TurmaService turmaService;
    private final AulaService aulaService;
    private final DisciplinaService disciplinaService;

    public HomeController(AlunoService alunoService, TurmaService turmaService,
                          AulaService aulaService, DisciplinaService disciplinaService) {
        this.alunoService = alunoService;
        this.turmaService = turmaService;
        this.aulaService = aulaService;
        this.disciplinaService = disciplinaService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalAlunos", alunoService.contarAlunos());
        model.addAttribute("alunosAtivos", alunoService.contarAlunosAtivos());
        model.addAttribute("totalTurmas", turmaService.contarTurmas());
        model.addAttribute("turmasAbertas", turmaService.contarTurmasAbertas());
        model.addAttribute("totalAulas", aulaService.contarAulas());
        model.addAttribute("aulasPlaneadas", aulaService.contarAulasPlaneadas());
        model.addAttribute("totalDisciplinas", disciplinaService.contarDisciplinas());
        return "index";
    }
}
