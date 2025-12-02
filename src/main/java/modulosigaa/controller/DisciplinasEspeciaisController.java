package modulosigaa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import modulosigaa.utils.Navegacao;

public class DisciplinasEspeciaisController {

    @FXML
    public void initialize() {
        System.out.println("Tela de Disciplinas Especiais carregada.");
    }

    @FXML
    public void handleVoltar(ActionEvent event) {
        Navegacao.navegar(event, "/modulosigaa/view/TelaInicial.fxml", "Portal do Orientador");
    }
}