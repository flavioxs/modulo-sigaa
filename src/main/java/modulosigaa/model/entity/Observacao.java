package modulosigaa.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Observacao {
    private int idObservacao;
    private String textoObservacao;
    private LocalDate dataObservacao;
    private LocalTime horarioObservacao;
    private int idOrientador;
    private int matriculaAluno;

    public Observacao() {}

    public int getIdObservacao() { return idObservacao; }
    public void setIdObservacao(int idObservacao) { this.idObservacao = idObservacao; }

    public String getTextoObservacao() { return textoObservacao; }
    public void setTextoObservacao(String textoObservacao) { this.textoObservacao = textoObservacao; }

    public LocalDate getDataObservacao() { return dataObservacao; }
    public void setDataObservacao(LocalDate dataObservacao) { this.dataObservacao = dataObservacao; }

    public LocalTime getHorarioObservacao() { return horarioObservacao; }
    public void setHorarioObservacao(LocalTime horarioObservacao) { this.horarioObservacao = horarioObservacao; }

    public int getIdOrientador() { return idOrientador; }
    public void setIdOrientador(int idOrientador) { this.idOrientador = idOrientador; }

    public int getMatriculaAluno() { return matriculaAluno; }
    public void setMatriculaAluno(int matriculaAluno) { this.matriculaAluno = matriculaAluno; }
}