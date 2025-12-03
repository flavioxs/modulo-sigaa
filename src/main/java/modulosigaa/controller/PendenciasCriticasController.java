package modulosigaa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modulosigaa.model.entity.Aluno;
import modulosigaa.model.enums.StatusRisco;
import modulosigaa.service.AlunoService;
import modulosigaa.utils.Navegacao;
import modulosigaa.utils.SessaoUsuario;

import java.util.List;

public class PendenciasCriticasController {

    @FXML private VBox listaVBox;

    private final AlunoService alunoService = new AlunoService();

    @FXML
    public void initialize() {
        carregarAlunosCriticos();
    }

    private void carregarAlunosCriticos() {
        listaVBox.getChildren().clear();
        listaVBox.setSpacing(15);

        List<Aluno> criticos = alunoService.listarAlunosCriticos();

        if (criticos.isEmpty()) {
            Label vazio = new Label("Nenhum aluno em situação de risco encontrado.");
            vazio.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");
            listaVBox.getChildren().add(vazio);
            return;
        }

        for (Aluno aluno : criticos) {
            listaVBox.getChildren().add(criarCardAlunoRisco(aluno));
        }
    }

    private HBox criarCardAlunoRisco(Aluno aluno) {
        HBox card = new HBox(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(15));
        
        boolean isCritico = aluno.getStatusRisco() == StatusRisco.CRITICO;
        String corBorda = isCritico ? "#e53935" : "#fb8c00";
        String corTextoStatus = isCritico ? "#c62828" : "#ef6c00";
        String bgStatus = isCritico ? "#ffebee" : "#fff3e0";

        card.setStyle("-fx-background-color: white; -fx-border-color: " + corBorda + "; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-width: 0 0 0 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1);");

        VBox infoBox = new VBox(3);
        Label lblNome = new Label(aluno.getNome());
        lblNome.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        
        Label lblMatricula = new Label("Matrícula: " + aluno.getMatricula() + " | IRA: " + aluno.getIra());
        lblMatricula.setStyle("-fx-text-fill: #666;");
        
        infoBox.getChildren().addAll(lblNome, lblMatricula);

        Label lblStatus = new Label(isCritico ? "RISCO DE JUBILAMENTO" : "MONITORAMENTO");
        lblStatus.setStyle("-fx-background-color: " + bgStatus + "; -fx-text-fill: " + corTextoStatus + "; -fx-font-weight: bold; -fx-padding: 5 10; -fx-background-radius: 15; -fx-font-size: 11px;");

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnAnalisar = new Button("Analisar Grade");
        btnAnalisar.setStyle("-fx-background-color: " + corBorda + "; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;");
        btnAnalisar.setOnAction(e -> {
            SessaoUsuario.getInstance().setAlunoSelecionado(aluno);
            Navegacao.navegar(e, "/modulosigaa/view/VisualizarGradeAcademicaPesquisa.fxml", "Análise de Risco - " + aluno.getNome());
        });

        card.getChildren().addAll(infoBox, spacer, lblStatus, btnAnalisar);
        return card;
    }

    @FXML
    public void handleVoltar(ActionEvent event) {
        Navegacao.navegar(event, "/modulosigaa/view/TelaInicial.fxml", "Portal do Orientador");
    }
}