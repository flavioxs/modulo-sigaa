package modulosigaa;

import java.sql.Connection;
import java.sql.SQLException;
import modulosigaa.db.DBConnection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando tentativa de conexão...");

        try (Connection conn = DBConnection.getConnection()) {
            
            if (conn != null) {
                System.out.println("✅ CONEXÃO REALIZADA COM SUCESSO!");
                System.out.println("Status da conexão: " + (conn.isValid(2) ? "Aberta e Válida" : "Inválida"));
            } else {
                System.out.println("⚠️ Conexão retornou nulo (algo estranho aconteceu).");
            }

        } catch (RuntimeException e) {
            System.out.println("❌ FALHA NA CONEXÃO.");
            System.out.println("Erro: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("Detalhe técnico: " + e.getCause().getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar status da conexão: " + e.getMessage());
        }
    }
}