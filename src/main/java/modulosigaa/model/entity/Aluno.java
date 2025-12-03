package modulosigaa.model.entity;

import modulosigaa.model.enums.StatusRisco;

public class Aluno {
    private int matricula;
    private String nome;
    private double ira;
    private String emailInstitucional;
    private StatusRisco statusRisco;
    private int idOrientador;
    private int idProfessor; 

    public Aluno() {}

    public Aluno(int matricula, String nome, double ira, String email, StatusRisco statusRisco) {
        this.matricula = matricula;
        this.nome = nome;
        this.ira = ira;
        this.emailInstitucional = email;
        this.statusRisco = statusRisco;
    }

    public int getMatricula() { return matricula; }
    public void setMatricula(int matricula) { this.matricula = matricula; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getIra() { return ira; }
    public void setIra(double ira) { this.ira = ira; }

    public String getEmailInstitucional() { return emailInstitucional; }
    public void setEmailInstitucional(String emailInstitucional) { this.emailInstitucional = emailInstitucional; }

    public StatusRisco getStatusRisco() { return statusRisco; }
    public void setStatusRisco(StatusRisco statusRisco) { this.statusRisco = statusRisco; }

    public int getIdOrientador() { return idOrientador; }
    public void setIdOrientador(int idOrientador) { this.idOrientador = idOrientador; }

    public int getIdProfessor() { return idProfessor; }
    public void setIdProfessor(int idProfessor) { this.idProfessor = idProfessor; }

    @Override
    public String toString() {
        return nome + " (" + matricula + ")";
    }
}