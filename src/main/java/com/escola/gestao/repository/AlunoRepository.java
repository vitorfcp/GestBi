package com.escola.gestao.repository;

import com.escola.gestao.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByEmail(String email);
    Optional<Aluno> findByNumeroAluno(String numeroAluno);
    List<Aluno> findByNomeContainingIgnoreCase(String nome);
    List<Aluno> findByTurmaId(Long turmaId);
    List<Aluno> findByEstado(Aluno.EstadoAluno estado);
    List<Aluno> findByTurmaIsNull();
}
