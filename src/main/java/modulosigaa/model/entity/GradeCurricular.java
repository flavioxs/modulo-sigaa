package modulosigaa.model.entity;

import java.util.List;

public class GradeCurricular {
    private int idGrade;
    private String curso;
    private String anoGrade;
    private List<Disciplina> disciplinas;

    public GradeCurricular() {}

    public int getIdGrade() { return idGrade; }
    public void setIdGrade(int idGrade) { this.idGrade = idGrade; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getAnoGrade() { return anoGrade; }
    public void setAnoGrade(String anoGrade) { this.anoGrade = anoGrade; }

    public List<Disciplina> getDisciplinas() { return disciplinas; }
    public void setDisciplinas(List<Disciplina> disciplinas) { this.disciplinas = disciplinas; }
}