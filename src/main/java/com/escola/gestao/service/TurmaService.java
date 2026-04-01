package com.escola.gestao.service;

import com.escola.gestao.model.Turma;
import com.escola.gestao.repository.TurmaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;

    public TurmaService(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    public List<Turma> listarTodas() {
        return turmaRepository.findAll();
    }

    public Optional<Turma> buscarPorId(Long id) {
        return turmaRepository.findById(id);
    }

    public List<Turma> pesquisarPorNome(String nome) {
        return turmaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Turma> listarPorEstado(Turma.EstadoTurma estado) {
        return turmaRepository.findByEstado(estado);
    }

    public Turma guardar(Turma turma) {
        return turmaRepository.save(turma);
    }

    public void eliminar(Long id) {
        turmaRepository.deleteById(id);
    }

    public long contarTurmas() {
        return turmaRepository.count();
    }

    public long contarTurmasAbertas() {
        return turmaRepository.findByEstado(Turma.EstadoTurma.ABERTA).size();
    }
}
