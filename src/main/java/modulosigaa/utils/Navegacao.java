package modulosigaa.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Navegacao {

    public static void navegar(ActionEvent event, String fxmlPath, String titulo) {
        Object source = event.getSource();
        if (source instanceof Node) {
            Stage stage = (Stage) ((Node) source).getScene().getWindow();
            carregarTela(stage, fxmlPath, titulo);
        } else {
            mostrarErro("Erro Técnico", "Não foi possível identificar a janela a partir do evento.");
        }
    }

    public static void navegar(Node nodeReferencia, String fxmlPath, String titulo) {
        if (nodeReferencia != null && nodeReferencia.getScene() != null) {
            Stage stage = (Stage) nodeReferencia.getScene().getWindow();
            carregarTela(stage, fxmlPath, titulo);
        } else {
            mostrarErro("Erro Técnico", "Componente de referência inválido (nulo ou sem cena).");
        }
    }

    private static void carregarTela(Stage stage, String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(Navegacao.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("UERN - SIGAA - " + titulo);
            stage.centerOnScreen(); 
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro de Navegação", "Não foi possível carregar a tela: " + fxmlPath + "\n\nErro: " + e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            mostrarErro("Erro de Arquivo", "Arquivo FXML não encontrado: " + fxmlPath);
        }
    }

    private static void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}