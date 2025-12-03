package modulosigaa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import modulosigaa.utils.Navegacao;

public class TelaInicialController {

    @FXML
    private AnchorPane rootPane; 

    @FXML
    public void handleVisualizarGradeCurricular(ActionEvent event) {
        Navegacao.navegar(rootPane, "/modulosigaa/view/BuscaPorAluno.fxml", "Buscar Aluno");
    }

    @FXML
    public void handleDisciplinasEspeciais(ActionEvent event) {
        Navegacao.navegar(rootPane, "/modulosigaa/view/DisciplinasEspeciais.fxml", "Disciplinas Especiais");
    }

    @FXML
    public void handleListarPendenciasCriticas(ActionEvent event) {
        Navegacao.navegar(rootPane, "/modulosigaa/view/ListaAlunosPendenciasCriticas.fxml", "Pendências Críticas");
    }

    @FXML
    public void handleVisualizarConsultasAgendadas(ActionEvent event) {
        Navegacao.navegar(rootPane, "/modulosigaa/view/ConsultasAgendadas.fxml", "Consultas Agendadas");
    }
}