package modulosigaa.model.entity;

public class Professor {
    private int idProfessor;
    private String nome;
    private String emailInstitucional;
    private String areaAtuacao;

    public Professor() {}

    public Professor(int idProfessor, String nome, String email, String area) {
        this.idProfessor = idProfessor;
        this.nome = nome;
        this.emailInstitucional = email;
        this.areaAtuacao = area;
    }

    public int getIdProfessor() { return idProfessor; }
    public void setIdProfessor(int idProfessor) { this.idProfessor = idProfessor; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmailInstitucional() { return emailInstitucional; }
    public void setEmailInstitucional(String emailInstitucional) { this.emailInstitucional = emailInstitucional; }

    public String getAreaAtuacao() { return areaAtuacao; }
    public void setAreaAtuacao(String areaAtuacao) { this.areaAtuacao = areaAtuacao; }
}