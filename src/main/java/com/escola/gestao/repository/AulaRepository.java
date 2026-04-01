package com.escola.gestao.repository;

import com.escola.gestao.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
    List<Aula> findByTurmaId(Long turmaId);
    List<Aula> findByEstado(Aula.EstadoAula estado);
    List<Aula> findByDataHoraInicioBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Aula> findByTurmaIdOrderByDataHoraInicioAsc(Long turmaId);
    List<Aula> findAllByOrderByDataHoraInicioAsc();
}
