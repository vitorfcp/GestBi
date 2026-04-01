package com.escola.gestao.service;

import com.escola.gestao.model.Disciplina;
import com.escola.gestao.repository.DisciplinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public List<Disciplina> listarTodas() {
        return disciplinaRepository.findAll();
    }

    public Optional<Disciplina> buscarPorId(Long id) {
        return disciplinaRepository.findById(id);
    }

    public Disciplina guardar(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public void eliminar(Long id) {
        disciplinaRepository.deleteById(id);
    }

    public long contarDisciplinas() {
        return disciplinaRepository.count();
    }
}
