package modulosigaa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import modulosigaa.model.entity.Aluno;
import modulosigaa.model.entity.SituacaoDisciplina;
import modulosigaa.repository.SituacaoDisciplinaRepository;
import modulosigaa.utils.Navegacao;
import modulosigaa.utils.SessaoUsuario;

import java.util.List;

public class GradeCurricularController {

    @FXML private Text nomeAlunoText;
    @FXML private Text matriculaAlunoText;
    @FXML private VBox gradeVBox; // VBox dentro do ScrollPane no FXML

    private final SituacaoDisciplinaRepository repository = new SituacaoDisciplinaRepository();

    @FXML
    public void initialize() {
        Aluno aluno = SessaoUsuario.getInstance().getAlunoSelecionado();

        if (aluno != null) {
            nomeAlunoText.setText("Aluno: " + aluno.getNome());
            matriculaAlunoText.setText("Matrícula: " + aluno.getMatricula());
            carregarGrade(aluno.getMatricula());
        } else {
            nomeAlunoText.setText("Nenhum aluno selecionado.");
        }
    }

    private void carregarGrade(int matricula) {
        gradeVBox.getChildren().clear();
        List<SituacaoDisciplina> historico = repository.buscarPorAluno(matricula);

        if (historico.isEmpty()) {
            gradeVBox.getChildren().add(new Label("Nenhum histórico encontrado para este aluno."));
            return;
        }

        for (SituacaoDisciplina sd : historico) {
            gradeVBox.getChildren().add(criarItemDisciplina(sd));
        }
    }

    private HBox criarItemDisciplina(SituacaoDisciplina sd) {
        HBox card = new HBox();
        card.setPadding(new Insets(10));
        card.setSpacing(10);
        
        String corFundo;
        String corTexto = "white";
        String statusTexto;

        if (sd.getStatusDisciplina() != null) {
            switch (sd.getStatusDisciplina()) {
                case CUMPRIDA: // Aprovado
                    corFundo = "#28a745"; 
                    statusTexto = "APROVADO";
                    break;
                case MATRICULADO: // Cursando
                    corFundo = "#007bff"; 
                    statusTexto = "CURSANDO";
                    break;
                case PENDENTE: // Reprovado
                    corFundo = "#dc3545"; 
                    statusTexto = "REPROVADO";
                    break;
                case APROVEITADA:
                    corFundo = "#17a2b8"; 
                    statusTexto = "APROVEITADO";
                    break;
                default:
                    corFundo = "#6c757d"; 
                    statusTexto = "DESCONHECIDO";
            }
        } else {
            corFundo = "#6c757d";
            statusTexto = "PENDENTE";
        }

        card.setStyle("-fx-background-color: " + corFundo + "; -fx-background-radius: 5;");

        Label lblNome = new Label(sd.getDisciplinaDetalhada().getNome());
        lblNome.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        lblNome.setStyle("-fx-text-fill: " + corTexto + ";");
        lblNome.setPrefWidth(300);

        Label lblStatus = new Label(statusTexto);
        lblStatus.setStyle("-fx-text-fill: " + corTexto + "; -fx-font-weight: bold;");
        lblStatus.setPrefWidth(100);

        Label lblNota = new Label("Nota: " + sd.getNota());
        lblNota.setStyle("-fx-text-fill: " + corTexto + ";");

        card.getChildren().addAll(lblNome, lblStatus, lblNota);
        return card;
    }

    @FXML
    public void handleVoltar(ActionEvent event) {
        Navegacao.navegar(event, "/modulosigaa/view/BuscaPorAluno.fxml", "Consultar Alunos");
    }
    
    @FXML public void handleImprimir() { System.out.println("Imprimindo..."); }
    @FXML public void handleVisualizarGradeCurricular() {}
    @FXML public void handleDisciplinasEspeciais() {}
    @FXML public void handleListarPendenciasCriticas() {}
    @FXML public void handleVisualizarConsultasAgendadas() {}
    @FXML public void handleVoltar2() {}
}