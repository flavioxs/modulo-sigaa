package modulosigaa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import modulosigaa.model.entity.Aluno;
import modulosigaa.model.entity.SituacaoDisciplina;
import modulosigaa.repository.SituacaoDisciplinaRepository;
import modulosigaa.utils.Navegacao;
import modulosigaa.utils.SessaoUsuario;

import java.util.ArrayList;
import java.util.List;

public class GradeCurricularController {

    @FXML private Text nomeAlunoText;
    @FXML private Text matriculaAlunoText;
    @FXML private VBox gradeVBox;

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
        gradeVBox.setSpacing(20);

        List<SituacaoDisciplina> historico = repository.buscarPorAluno(matricula);

        if (historico.isEmpty()) {
            gradeVBox.getChildren().add(new Label("Histórico não encontrado ou vazio."));
            return;
        }

 
        int maxPeriodos = 10;
        List<List<SituacaoDisciplina>> periodos = new ArrayList<>();
        for (int i = 0; i < maxPeriodos; i++) {
            periodos.add(new ArrayList<>());
        }

        for (SituacaoDisciplina sd : historico) {
            if (sd.getDisciplinaDetalhada() != null) {
                int p = sd.getDisciplinaDetalhada().getPeriodo();
                if (p >= 1 && p <= maxPeriodos) {
                    periodos.get(p - 1).add(sd);
                }
            }
        }

        for (int i = 0; i < maxPeriodos; i++) {
            List<SituacaoDisciplina> disciplinasDoPeriodo = periodos.get(i);
            
            
            if (disciplinasDoPeriodo.isEmpty()) continue; 

            Label lblPeriodo = new Label((i + 1) + "º Período");
            lblPeriodo.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
            lblPeriodo.setStyle("-fx-text-fill: #3f5ad8; -fx-padding: 0 0 5 0;");
            gradeVBox.getChildren().add(lblPeriodo);

    
            HBox linhaSemestre = new HBox(15);
            linhaSemestre.setPrefWidth(Double.MAX_VALUE);
            linhaSemestre.setPadding(new Insets(5));
            
            for (SituacaoDisciplina sd : disciplinasDoPeriodo) {
                linhaSemestre.getChildren().add(criarCaixinhaDisciplina(sd));
            }
            
            ScrollPane scrollLinha = new ScrollPane(linhaSemestre);
            scrollLinha.setFitToHeight(true);
            scrollLinha.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
            scrollLinha.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollLinha.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
            
            gradeVBox.getChildren().add(scrollLinha);
        }
    }

    private VBox criarCaixinhaDisciplina(SituacaoDisciplina sd) {

        VBox box = new VBox(5);
        box.setPadding(new Insets(10));
        box.setPrefWidth(150);  
        box.setPrefHeight(110); 
        box.setAlignment(Pos.CENTER);
        
        String corFundo = "#f5f5f5"; 
        String corBorda = "#cccccc";
        String corTexto = "#333333";
        
        if (sd.getStatusDisciplina() != null) {
            switch (sd.getStatusDisciplina()) {
                case CUMPRIDA: 
                    corFundo = "#d4edda"; 
                    corBorda = "#c3e6cb";
                    corTexto = "#155724";
                    break;
                case MATRICULADO: 
                    corFundo = "#cce5ff";
                    corBorda = "#b8daff";
                    corTexto = "#004085";
                    break;
                case PENDENTE: 
                    corFundo = "#f8d7da";
                    corBorda = "#f5c6cb";
                    corTexto = "#721c24";
                    break;
                case APROVEITADA:
                    corFundo = "#fff3cd";
                    corBorda = "#ffeeba";
                    corTexto = "#856404";
                    break;
                default: break;
            }
        }

       
        box.setStyle("-fx-background-color: " + corFundo + "; " +
                     "-fx-border-color: " + corBorda + "; " +
                     "-fx-border-radius: 8; -fx-background-radius: 8; " +
                     "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1);");

        
        
        String nome = sd.getDisciplinaDetalhada() != null ? sd.getDisciplinaDetalhada().getNome() : "Desconhecida";
        Label lblNome = new Label(nome);
        lblNome.setWrapText(true);
        lblNome.setAlignment(Pos.CENTER);
        lblNome.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        lblNome.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        lblNome.setStyle("-fx-text-fill: " + corTexto + ";");
        VBox.setVgrow(lblNome, Priority.ALWAYS);

        int ch = sd.getDisciplinaDetalhada() != null ? sd.getDisciplinaDetalhada().getCargaHoraria() : 0;
        Label lblCarga = new Label("CH: " + ch + "h");
        lblCarga.setFont(Font.font("Verdana", 9));
        lblCarga.setStyle("-fx-text-fill: " + corTexto + ";");

        Label lblNota = new Label(sd.getNota() > 0 ? String.format("Nota: %.1f", sd.getNota()) : "");
        lblNota.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        lblNota.setStyle("-fx-text-fill: " + corTexto + ";");

        box.getChildren().addAll(lblNome, lblCarga, lblNota);
        return box;
    }


    @FXML
    public void handleVoltar(ActionEvent event) {
        Navegacao.navegar(event, "/modulosigaa/view/BuscaPorAluno.fxml", "Consultar Alunos");
    }

    @FXML
    public void handleVoltar2(ActionEvent event) {
        Navegacao.navegar(event, "/modulosigaa/view/BuscaPorAluno.fxml", "Consultar Alunos");
    }
    
    @FXML
    public void handleImprimir() {
        System.out.println("Enviando grade para impressão...");
    }
    
    @FXML public void handleVisualizarGradeCurricular() {}
    @FXML public void handleDisciplinasEspeciais() {}
    @FXML public void handleListarPendenciasCriticas() {}
    @FXML public void handleVisualizarConsultasAgendadas() {}
}