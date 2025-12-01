package modulosigaa.model.entity;

public class Orientador {
    private int idOrientador;
    private String nome;
    private String emailInstitucional;

    public Orientador() {}

    public Orientador(int idOrientador, String nome, String emailInstitucional) {
        this.idOrientador = idOrientador;
        this.nome = nome;
        this.emailInstitucional = emailInstitucional;
    }

    public int getIdOrientador() { return idOrientador; }
    public void setIdOrientador(int idOrientador) { this.idOrientador = idOrientador; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmailInstitucional() { return emailInstitucional; }
    public void setEmailInstitucional(String emailInstitucional) { this.emailInstitucional = emailInstitucional; }
}