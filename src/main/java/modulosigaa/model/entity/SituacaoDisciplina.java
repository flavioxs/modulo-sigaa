package modulosigaa.model.entity;

import modulosigaa.model.enums.StatusRisco;

public class SituacaoDisciplina {
    private int matriculaAluno;
    private int idDisciplina;
    private StatusRisco statusDisciplina; 
    private double nota;
    
    private Disciplina disciplinaDetalhada; 

    public SituacaoDisciplina() {}

    public int getMatriculaAluno() { return matriculaAluno; }
    public void setMatriculaAluno(int matriculaAluno) { this.matriculaAluno = matriculaAluno; }

    public int getIdDisciplina() { return idDisciplina; }
    public void setIdDisciplina(int idDisciplina) { this.idDisciplina = idDisciplina; }

    public StatusRisco getStatusDisciplina() { return statusDisciplina; }
    public void setStatusDisciplina(StatusRisco statusDisciplina) { this.statusDisciplina = statusDisciplina; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }

    public Disciplina getDisciplinaDetalhada() { return disciplinaDetalhada; }
    public void setDisciplinaDetalhada(Disciplina disciplinaDetalhada) { this.disciplinaDetalhada = disciplinaDetalhada; }
}