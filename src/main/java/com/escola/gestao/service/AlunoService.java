package com.escola.gestao.service;

import com.escola.gestao.model.Aluno;
import com.escola.gestao.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public List<Aluno> pesquisarPorNome(String nome) {
        return alunoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Aluno> listarPorTurma(Long turmaId) {
        return alunoRepository.findByTurmaId(turmaId);
    }

    public List<Aluno> listarSemTurma() {
        return alunoRepository.findByTurmaIsNull();
    }

    public Aluno guardar(Aluno aluno) {
        if (aluno.getNumeroAluno() == null || aluno.getNumeroAluno().isBlank()) {
            aluno.setNumeroAluno(gerarNumeroAluno());
        }
        return alunoRepository.save(aluno);
    }

    public Aluno guardarComFoto(Aluno aluno, MultipartFile foto) throws IOException {
        if (foto != null && !foto.isEmpty()) {
            String fotoUrl = guardarFoto(foto);
            aluno.setFotoUrl(fotoUrl);
        }
        return guardar(aluno);
    }

    public String guardarFoto(MultipartFile foto) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String originalFilename = foto.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = UUID.randomUUID().toString() + extension;
        Path filePath = uploadPath.resolve(filename);
        Files.copy(foto.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + filename;
    }

    public void eliminar(Long id) {
        alunoRepository.deleteById(id);
    }

    public long contarAlunos() {
        return alunoRepository.count();
    }

    public long contarAlunosAtivos() {
        return alunoRepository.findByEstado(Aluno.EstadoAluno.ATIVO).size();
    }

    private String gerarNumeroAluno() {
        long count = alunoRepository.count() + 1;
        return String.format("ALU%05d", count);
    }
}
