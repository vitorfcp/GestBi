package com.escola.gestao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da turma é obrigatório")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    @Column(unique = true)
    private String nome;

    @Size(max = 200)
    private String descricao;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDate dataInicio;

    private LocalDate dataFim;

    @NotNull(message = "O número máximo de alunos é obrigatório")
    @Min(value = 1, message = "A turma deve ter pelo menos 1 vaga")
    @Max(value = 100, message = "A turma não pode ter mais de 100 vagas")
    private Integer maxAlunos;

    private String sala;

    @Enumerated(EnumType.STRING)
    private EstadoTurma estado = EstadoTurma.ABERTA;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Aluno> alunos = new ArrayList<>();

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Aula> aulas = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    public enum EstadoTurma {
        ABERTA, EM_CURSO, FECHADA, CANCELADA
    }

    public Turma() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public Integer getMaxAlunos() { return maxAlunos; }
    public void setMaxAlunos(Integer maxAlunos) { this.maxAlunos = maxAlunos; }

    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }

    public EstadoTurma getEstado() { return estado; }
    public void setEstado(EstadoTurma estado) { this.estado = estado; }

    public List<Aluno> getAlunos() { return alunos; }
    public void setAlunos(List<Aluno> alunos) { this.alunos = alunos; }

    public List<Aula> getAulas() { return aulas; }
    public void setAulas(List<Aula> aulas) { this.aulas = aulas; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public int getNumeroAlunosInscritos() {
        return alunos != null ? alunos.size() : 0;
    }

    public boolean temVagas() {
        return getNumeroAlunosInscritos() < maxAlunos;
    }
}
