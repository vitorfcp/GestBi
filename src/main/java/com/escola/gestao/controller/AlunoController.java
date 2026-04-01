package com.escola.gestao.controller;

import com.escola.gestao.model.Aluno;
import com.escola.gestao.service.AlunoService;
import com.escola.gestao.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;
    private final TurmaService turmaService;

    public AlunoController(AlunoService alunoService, TurmaService turmaService) {
        this.alunoService = alunoService;
        this.turmaService = turmaService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String pesquisa, Model model) {
        if (pesquisa != null && !pesquisa.isBlank()) {
            model.addAttribute("alunos", alunoService.pesquisarPorNome(pesquisa));
            model.addAttribute("pesquisa", pesquisa);
        } else {
            model.addAttribute("alunos", alunoService.listarTodos());
        }
        return "alunos/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("aluno", new Aluno());
        model.addAttribute("turmas", turmaService.listarTodas());
        model.addAttribute("estados", Aluno.EstadoAluno.values());
        return "alunos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Aluno aluno,
                          BindingResult result,
                          @RequestParam(value = "foto", required = false) MultipartFile foto,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("turmas", turmaService.listarTodas());
            model.addAttribute("estados", Aluno.EstadoAluno.values());
            return "alunos/formulario";
        }
        try {
            alunoService.guardarComFoto(aluno, foto);
            redirectAttributes.addFlashAttribute("mensagem", "Aluno guardado com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao guardar foto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
        }
        return "redirect:/alunos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return alunoService.buscarPorId(id).map(aluno -> {
            model.addAttribute("aluno", aluno);
            model.addAttribute("turmas", turmaService.listarTodas());
            model.addAttribute("estados", Aluno.EstadoAluno.values());
            return "alunos/formulario";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("mensagem", "Aluno não encontrado!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            return "redirect:/alunos";
        });
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return alunoService.buscarPorId(id).map(aluno -> {
            model.addAttribute("aluno", aluno);
            return "alunos/ficha";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("mensagem", "Aluno não encontrado!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            return "redirect:/alunos";
        });
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        alunoService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensagem", "Aluno eliminado com sucesso!");
        redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        return "redirect:/alunos";
    }
}
