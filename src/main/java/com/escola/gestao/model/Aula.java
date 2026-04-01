package com.escola.gestao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "aulas")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O tema da aula é obrigatório")
    @Size(min = 2, max = 200)
    private String tema;

    @Size(max = 1000)
    private String descricao;

    @NotNull(message = "A data/hora de início é obrigatória")
    private LocalDateTime dataHoraInicio;

    @NotNull(message = "A data/hora de fim é obrigatória")
    private LocalDateTime dataHoraFim;

    private String sala;

    @Size(max = 100)
    private String professor;

    @Size(max = 500)
    private String conteudoProgramatico;

    @Size(max = 500)
    private String objetivos;

    @Size(max = 500)
    private String recursos;

    @Enumerated(EnumType.STRING)
    private EstadoAula estado = EstadoAula.PLANEADA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_id")
    @NotNull(message = "A turma é obrigatória")
    private Turma turma;

    public enum EstadoAula {
        PLANEADA, EM_CURSO, CONCLUIDA, CANCELADA
    }

    public Aula() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }

    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public void setDataHoraFim(LocalDateTime dataHoraFim) { this.dataHoraFim = dataHoraFim; }

    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }

    public String getProfessor() { return professor; }
    public void setProfessor(String professor) { this.professor = professor; }

    public String getConteudoProgramatico() { return conteudoProgramatico; }
    public void setConteudoProgramatico(String conteudoProgramatico) { this.conteudoProgramatico = conteudoProgramatico; }

    public String getObjetivos() { return objetivos; }
    public void setObjetivos(String objetivos) { this.objetivos = objetivos; }

    public String getRecursos() { return recursos; }
    public void setRecursos(String recursos) { this.recursos = recursos; }

    public EstadoAula getEstado() { return estado; }
    public void setEstado(EstadoAula estado) { this.estado = estado; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
}
