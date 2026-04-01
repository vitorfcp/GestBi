package com.escola.gestao.repository;

import com.escola.gestao.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    Optional<Turma> findByNome(String nome);
    List<Turma> findByEstado(Turma.EstadoTurma estado);
    List<Turma> findByNomeContainingIgnoreCase(String nome);
}
