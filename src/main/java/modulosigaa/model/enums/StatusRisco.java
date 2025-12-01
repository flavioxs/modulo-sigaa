package modulosigaa.model.enums;

public enum StatusRisco {
    REGULAR("Normal"),
    ATENCAO("Monitoramento"),
    CRITICO("Risco");

    private final String descricaoBanco;

    StatusRisco(String descricaoBanco) {
        this.descricaoBanco = descricaoBanco;
    }

    public String getDescricaoBanco() {
        return descricaoBanco;
    }

    // Método auxiliar para converter do Banco para Enum
    public static StatusRisco fromString(String texto) {
        for (StatusRisco status : StatusRisco.values()) {
            if (status.descricaoBanco.equalsIgnoreCase(texto)) {
                return status;
            }
        }
        return REGULAR; // Valor padrão caso não encontre
    }
}