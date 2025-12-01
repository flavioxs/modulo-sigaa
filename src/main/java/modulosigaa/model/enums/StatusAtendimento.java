package modulosigaa.model.enums;

public enum StatusAtendimento {
    SOLICITADO("Agendado"),
    CONFIRMADO("Concluído"),
    RECUSADO("Cancelado");

    private final String descricaoBanco;

    StatusAtendimento(String descricaoBanco) {
        this.descricaoBanco = descricaoBanco;
    }

    public String getDescricaoBanco() {
        return descricaoBanco;
    }

    public static StatusAtendimento fromString(String texto) {
        for (StatusAtendimento status : StatusAtendimento.values()) {
            if (status.descricaoBanco.equalsIgnoreCase(texto)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status desconhecido no banco: " + texto);
    }
}