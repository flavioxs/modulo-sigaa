package modulosigaa.controller;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modulosigaa.model.entity.SolicitacaoDisciplina;
import modulosigaa.service.SolicitacaoService;
import modulosigaa.utils.Navegacao;

public class AlunosSolicitantesController {

    @FXML private VBox solicitantesVBox;
    private final SolicitacaoService service = new SolicitacaoService();

    @FXML
    public void initialize() {
        carregarSolicitacoes();
    }

    private void carregarSolicitacoes() {
        solicitantesVBox.getChildren().clear();
        List<SolicitacaoDisciplina> pendentes = service.listarSolicitacoesPendentes();

        if (pendentes.isEmpty()) {
            solicitantesVBox.getChildren().add(new Label("Não há solicitações pendentes."));
            return;
        }

        for (SolicitacaoDisciplina sd : pendentes) {
            solicitantesVBox.getChildren().add(criarCardSolicitacao(sd));
        }
    }

    private HBox criarCardSolicitacao(SolicitacaoDisciplina sd) {
        HBox card = new HBox(10);
        card.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: #b3bef1; -fx-border-radius: 5;");
        card.setAlignment(Pos.CENTER_LEFT);

        VBox info = new VBox(5);
        Label lblAluno = new Label("Aluno: " + sd.getNomeAluno() + " (" + sd.getMatriculaAluno() + ")");
        lblAluno.setStyle("-fx-font-weight: bold;");
        Label lblDisciplina = new Label("Disciplina: " + sd.getNomeDisciplina());
        Label lblJustificativa = new Label("Motivo: " + sd.getJustificativa());
        lblJustificativa.setWrapText(true);
        lblJustificativa.setMaxWidth(300);
        
        info.getChildren().addAll(lblAluno, lblDisciplina, lblJustificativa);

        Button btnAceitar = new Button("Aceitar");
        btnAceitar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        btnAceitar.setOnAction(e -> processarSolicitacao(sd, true));

        Button btnRecusar = new Button("Recusar");
        btnRecusar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        btnRecusar.setOnAction(e -> processarSolicitacao(sd, false));

        card.getChildren().addAll(info, btnAceitar, btnRecusar);
        return card;
    }

    private void processarSolicitacao(SolicitacaoDisciplina sd, boolean aceitar) {
        try {
            if (aceitar) {
                service.aprovarSolicitacao(sd);
            } else {
                service.recusarSolicitacao(sd);
            }
            carregarSolicitacoes(); // Atualiza a lista
            new Alert(Alert.AlertType.INFORMATION, "Solicitação processada.").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + e.getMessage()).show();
        }
    }

    @FXML public void handleFinalizarAnalise(ActionEvent event) {
        Navegacao.navegar(event, "/modulosigaa/view/Sucesso.fxml", "Sucesso");
    }

    @FXML public void handleVisualizarGradeCurricular(ActionEvent event) { Navegacao.navegar(solicitantesVBox, "/modulosigaa/view/BuscaPorAluno.fxml", "Grade"); }
    @FXML public void handleDisciplinasEspeciais(ActionEvent event) { Navegacao.navegar(solicitantesVBox, "/modulosigaa/view/DisciplinasEspeciais.fxml", "Disciplinas"); }
    @FXML public void handleListarPendenciasCriticas(ActionEvent event) { Navegacao.navegar(solicitantesVBox, "/modulosigaa/view/ListaAlunosPendenciasCriticas.fxml", "Pendências"); }
    @FXML public void handleVisualizarConsultasAgendadas(ActionEvent event) { Navegacao.navegar(solicitantesVBox, "/modulosigaa/view/ConsultasAgendadas.fxml", "Consultas"); }
}