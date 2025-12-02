package modulosigaa.controller;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import modulosigaa.dao.ProfessorDAO;
import modulosigaa.model.entity.Professor;
import modulosigaa.utils.Navegacao;

public class DisponibilidadeProfessoresController {

    @FXML private VBox professoresVBox;
    @FXML private Label tituloDisciplinaText; // Se existir no FXML

    private final ProfessorDAO professorDAO = new ProfessorDAO();

    @FXML
    public void initialize() {
        if (tituloDisciplinaText != null) {
            tituloDisciplinaText.setText("Lista Geral de Professores");
        }
        carregarProfessores();
    }

    private void carregarProfessores() {
        professoresVBox.getChildren().clear();
        List<Professor> professores = professorDAO.listarTodos();

        for (Professor p : professores) {
            Label lbl = new Label("• " + p.getNome() + " - " + p.getAreaAtuacao());
            lbl.setStyle("-fx-font-size: 14px; -fx-padding: 5;");
            professoresVBox.getChildren().add(lbl);
        }
    }

    @FXML public void handleVoltar(ActionEvent event) {
        Navegacao.navegar(event, "/modulosigaa/view/TelaInicial.fxml", "Portal do Orientador");
    }
}