package modulosigaa.utils;

import modulosigaa.model.entity.Aluno;

public class SessaoUsuario {
    private static SessaoUsuario instance;
    private Aluno alunoSelecionado;

    private SessaoUsuario() {}

    public static SessaoUsuario getInstance() {
        if (instance == null) {
            instance = new SessaoUsuario();
        }
        return instance;
    }

    public Aluno getAlunoSelecionado() {
        return alunoSelecionado;
    }

    public void setAlunoSelecionado(Aluno alunoSelecionado) {
        this.alunoSelecionado = alunoSelecionado;
    }
}