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


    public static StatusRisco fromString(String texto) {
        for (StatusRisco status : StatusRisco.values()) {
            if (status.descricaoBanco.equalsIgnoreCase(texto)) {
                return status;
            }
        }
        return REGULAR; 
    }
}