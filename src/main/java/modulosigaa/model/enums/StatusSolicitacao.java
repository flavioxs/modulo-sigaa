package modulosigaa.model.enums;

public enum StatusSolicitacao {
    PENDENTE("Pendente"),
    ACEITA("Aprovada"),
    RECUSADA("Rejeitada");

    private final String descricaoBanco;

    StatusSolicitacao(String descricaoBanco) {
        this.descricaoBanco = descricaoBanco;
    }

    public String getDescricaoBanco() {
        return descricaoBanco;
    }

    public static StatusSolicitacao fromString(String texto) {
        for (StatusSolicitacao status : StatusSolicitacao.values()) {
            if (status.descricaoBanco.equalsIgnoreCase(texto)) {
                return status;
            }
        }
        return PENDENTE;
    }
}