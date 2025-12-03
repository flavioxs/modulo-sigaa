package modulosigaa.model.entity;

import modulosigaa.model.enums.StatusSituacao; 

public class SituacaoDisciplina {
    private int matriculaAluno;
    private int idDisciplina;
    private StatusSituacao statusDisciplina; 
    private double nota;
    
    private Disciplina disciplinaDetalhada; 

    public SituacaoDisciplina() {}

    public int getMatriculaAluno() { return matriculaAluno; }
    public void setMatriculaAluno(int matriculaAluno) { this.matriculaAluno = matriculaAluno; }

    public int getIdDisciplina() { return idDisciplina; }
    public void setIdDisciplina(int idDisciplina) { this.idDisciplina = idDisciplina; }

    public StatusSituacao getStatusDisciplina() { return statusDisciplina; }
    public void setStatusDisciplina(StatusSituacao statusDisciplina) { this.statusDisciplina = statusDisciplina; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }

    public Disciplina getDisciplinaDetalhada() { return disciplinaDetalhada; }
    public void setDisciplinaDetalhada(Disciplina disciplinaDetalhada) { this.disciplinaDetalhada = disciplinaDetalhada; }
}