package modulosigaa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import modulosigaa.utils.Navegacao; 

public class BuscaPorAlunoController {

    @FXML
    private TextField nomeInput; 

    @FXML
    private TextField matriculaInput; 

    @FXML
    public void handlePesquisarAluno(ActionEvent event) {
        System.out.println("Pesquisando aluno: " + nomeInput.getText());
        Navegacao.navegar(event, "/modulosigaa/view/VisualizarGradeAcademicaPesquisa.fxml", "Grade Curricular");
    }

    @FXML
    public void handleVoltar(ActionEvent event) {
        Navegacao.navegar(event, "/modulosigaa/view/TelaInicial.fxml", "Portal do Orientador");
    }
    
    @FXML public void handleVisualizarGradeCurricular() {}
    @FXML public void handleDisciplinasEspeciais() {}
    @FXML public void handleListarPendenciasCriticas() {}
    @FXML public void handleVisualizarConsultasAgendadas() {}
}