package com.escola.gestao.service;

import com.escola.gestao.model.Aula;
import com.escola.gestao.repository.AulaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AulaService {

    private final AulaRepository aulaRepository;

    public AulaService(AulaRepository aulaRepository) {
        this.aulaRepository = aulaRepository;
    }

    public List<Aula> listarTodas() {
        return aulaRepository.findAllByOrderByDataHoraInicioAsc();
    }

    public Optional<Aula> buscarPorId(Long id) {
        return aulaRepository.findById(id);
    }

    public List<Aula> listarPorTurma(Long turmaId) {
        return aulaRepository.findByTurmaIdOrderByDataHoraInicioAsc(turmaId);
    }

    public List<Aula> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return aulaRepository.findByDataHoraInicioBetween(inicio, fim);
    }

    public Aula guardar(Aula aula) {
        return aulaRepository.save(aula);
    }

    public void eliminar(Long id) {
        aulaRepository.deleteById(id);
    }

    public long contarAulas() {
        return aulaRepository.count();
    }

    public long contarAulasPlaneadas() {
        return aulaRepository.findByEstado(Aula.EstadoAula.PLANEADA).size();
    }
}
