package modulosigaa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modulosigaa.db.DBConnection;

import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. Teste de Conexão com o Banco (Opcional, mas recomendado na inicialização)
        verificarBancoDeDados();

        try {
            // 2. Carregamento do FXML
            // O caminho deve iniciar com '/' e refletir a estrutura em resources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modulosigaa/view/TelaInicial.fxml"));
            Parent root = loader.load();

            // 3. Configuração da Cena e Palco
            Scene scene = new Scene(root);
            primaryStage.setTitle("UERN - SIGAA - Módulo Orientador Acadêmico");
            primaryStage.setScene(scene);
            
            // Impede que a janela fique muito pequena
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            
            primaryStage.show();
            System.out.println("Aplicação iniciada com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro fatal ao iniciar a aplicação: " + e.getMessage());
        }
    }

    private void verificarBancoDeDados() {
        System.out.println("Tentando conectar ao banco de dados...");
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexão com o banco estabelecida com sucesso.");
            } else {
                System.out.println("Conexão obtida, mas parece fechada ou nula.");
            }
        } catch (Exception e) {
            System.err.println("Falha ao conectar ao banco de dados. Verifique se o MySQL está rodando.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}