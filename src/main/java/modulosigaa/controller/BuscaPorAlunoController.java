package modulosigaa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import modulosigaa.utils.Navegacao; 

public class BuscaPorAlunoController {

    @FXML
    public void handlePesquisarAluno(ActionEvent event) {
        System.out.println("Pesquisando aluno...");
        // Exemplo: Simular que encontrou e ir para a grade
        // Em um cenário real, você validaria o aluno antes
        Navegacao.navegar(event, "/modulosigaa/view/VisualizarGradeAcademicaPesquisa.fxml", "Grade Curricular");
    }

    @FXML
    public void handleVoltar(ActionEvent event) {
        // Volta para a Tela Inicial
        Navegacao.navegar(event, "/modulosigaa/view/TelaInicial.fxml", "Portal do Orientador");
    }
    
    // --- Menus (caso existam nessa tela) ---
    // Como menus não passam 'Node' no evento, precisaríamos de um 'rootPane' aqui também.
    // Mas se o menu não for clicável nessa tela, pode deixar vazio.
    @FXML public void handleVisualizarGradeCurricular() {}
    @FXML public void handleDisciplinasEspeciais() {}
    @FXML public void handleListarPendenciasCriticas() {}
    @FXML public void handleVisualizarConsultasAgendadas() {}
}