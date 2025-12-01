package modulosigaa.model.enums;

public enum StatusSituacao {
    CUMPRIDA("Aprovado"),
    MATRICULADO("Cursando"),
    APROVEITADA("Aproveitado"),
    PENDENTE("Reprovado");

    private final String descricaoBanco;

    StatusSituacao(String descricaoBanco) {
        this.descricaoBanco = descricaoBanco;
    }

    public String getDescricaoBanco() {
        return descricaoBanco;
    }

    public static StatusSituacao fromString(String texto) {
        for (StatusSituacao status : StatusSituacao.values()) {
            if (status.descricaoBanco.equalsIgnoreCase(texto)) {
                return status;
            }
        }
        return PENDENTE;
    }
}