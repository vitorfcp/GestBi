package com.escola.gestao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    @Column(unique = true)
    private String email;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @Size(max = 20)
    private String telefone;

    @Size(max = 200)
    private String morada;

    @Size(max = 20)
    private String codigoPostal;

    @Size(max = 100)
    private String cidade;

    @Size(max = 100)
    private String nacionalidade;

    @Size(max = 20)
    private String documentoIdentificacao;

    @Column(name = "numero_aluno", unique = true)
    private String numeroAluno;

    private String fotoUrl;

    @Size(max = 500)
    private String observacoes;

    @Enumerated(EnumType.STRING)
    private EstadoAluno estado = EstadoAluno.ATIVO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_id")
    private Turma turma;

    public enum EstadoAluno {
        ATIVO, INATIVO, SUSPENSO, TRANSFERIDO
    }

    public Aluno() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getMorada() { return morada; }
    public void setMorada(String morada) { this.morada = morada; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    public String getDocumentoIdentificacao() { return documentoIdentificacao; }
    public void setDocumentoIdentificacao(String documentoIdentificacao) { this.documentoIdentificacao = documentoIdentificacao; }

    public String getNumeroAluno() { return numeroAluno; }
    public void setNumeroAluno(String numeroAluno) { this.numeroAluno = numeroAluno; }

    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public EstadoAluno getEstado() { return estado; }
    public void setEstado(EstadoAluno estado) { this.estado = estado; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
}
