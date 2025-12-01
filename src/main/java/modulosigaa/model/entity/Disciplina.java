package modulosigaa.model.entity;

public class Disciplina {
    private int idDisciplina;
    private String nome;
    private String areaConhecimento;
    private int cargaHoraria;
    private int periodo;
    private boolean obrigatoria;

    public Disciplina() {}

    public Disciplina(int id, String nome, String area, int carga, int periodo, boolean obrigatoria) {
        this.idDisciplina = id;
        this.nome = nome;
        this.areaConhecimento = area;
        this.cargaHoraria = carga;
        this.periodo = periodo;
        this.obrigatoria = obrigatoria;
    }

    public int getIdDisciplina() { return idDisciplina; }
    public void setIdDisciplina(int idDisciplina) { this.idDisciplina = idDisciplina; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getAreaConhecimento() { return areaConhecimento; }
    public void setAreaConhecimento(String areaConhecimento) { this.areaConhecimento = areaConhecimento; }

    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public int getPeriodo() { return periodo; }
    public void setPeriodo(int periodo) { this.periodo = periodo; }

    public boolean isObrigatoria() { return obrigatoria; }
    public void setObrigatoria(boolean obrigatoria) { this.obrigatoria = obrigatoria; }
    
    @Override
    public String toString() {
        return nome + " (" + cargaHoraria + "h)";
    }
}