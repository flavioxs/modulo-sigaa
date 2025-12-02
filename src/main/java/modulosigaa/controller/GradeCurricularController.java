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
import javafx.scene.layout.Pane;
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
                // Ajusta o índice (Período 1 vai no índice 0)
                if (p >= 1 && p <= maxPeriodos) {
                    periodos.get(p - 1).add(sd);
                }
            }
        }

        // 3. Renderiza cada período na tela
        for (int i = 0; i < maxPeriodos; i++) {
            List<SituacaoDisciplina> disciplinasDoPeriodo = periodos.get(i);
            
            
            if (disciplinasDoPeriodo.isEmpty()) continue; 

        
            Label lblPeriodo = new Label((i + 1) + "º Período");
            lblPeriodo.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
            lblPeriodo.setStyle("-fx-text-fill: #3f5ad8; -fx-padding: 0 0 5 0;");
            gradeVBox.getChildren().add(lblPeriodo);

    
            HBox linhaSemestre = new HBox(15); // Espaço horizontal entre matérias
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
        // Cria o "Card" da disciplina (quadradinho)
        VBox box = new VBox(5);
        box.setPadding(new Insets(10));
        box.setPrefWidth(150);  // Largura fixa para ficarem uniformes
        box.setPrefHeight(110); // Altura fixa
        box.setAlignment(Pos.CENTER);
        
        // Define as cores com base no Status
        String corFundo = "#f5f5f5"; // Cinza claro (Padrão/Pendente)
        String corBorda = "#cccccc";
        String corTexto = "#333333";
        
        if (sd.getStatusDisciplina() != null) {
            switch (sd.getStatusDisciplina()) {
                case CUMPRIDA: // Aprovado -> Verde
                    corFundo = "#d4edda"; 
                    corBorda = "#c3e6cb";
                    corTexto = "#155724";
                    break;
                case MATRICULADO: // Cursando -> Azul
                    corFundo = "#cce5ff";
                    corBorda = "#b8daff";
                    corTexto = "#004085";
                    break;
                case PENDENTE: // Reprovado -> Vermelho (ou Rosa claro)
                    corFundo = "#f8d7da";
                    corBorda = "#f5c6cb";
                    corTexto = "#721c24";
                    break;
                case APROVEITADA: // Aproveitado -> Amarelo/Laranja claro
                    corFundo = "#fff3cd";
                    corBorda = "#ffeeba";
                    corTexto = "#856404";
                    break;
                default: break;
            }
        }

        // Aplica o estilo CSS inline
        box.setStyle("-fx-background-color: " + corFundo + "; " +
                     "-fx-border-color: " + corBorda + "; " +
                     "-fx-border-radius: 8; -fx-background-radius: 8; " +
                     "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1);");

        // --- CONTEÚDO DO CARD ---
        
        // Nome da Disciplina
        String nome = sd.getDisciplinaDetalhada() != null ? sd.getDisciplinaDetalhada().getNome() : "Desconhecida";
        Label lblNome = new Label(nome);
        lblNome.setWrapText(true);
        lblNome.setAlignment(Pos.CENTER);
        lblNome.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        lblNome.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        lblNome.setStyle("-fx-text-fill: " + corTexto + ";");
        // Faz o nome crescer para ocupar o espaço disponível verticalmente
        VBox.setVgrow(lblNome, Priority.ALWAYS);

        // Carga Horária
        int ch = sd.getDisciplinaDetalhada() != null ? sd.getDisciplinaDetalhada().getCargaHoraria() : 0;
        Label lblCarga = new Label("CH: " + ch + "h");
        lblCarga.setFont(Font.font("Verdana", 9));
        lblCarga.setStyle("-fx-text-fill: " + corTexto + ";");

        // Nota (Só mostra se for maior que 0)
        Label lblNota = new Label(sd.getNota() > 0 ? String.format("Nota: %.1f", sd.getNota()) : "");
        lblNota.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        lblNota.setStyle("-fx-text-fill: " + corTexto + ";");

        box.getChildren().addAll(lblNome, lblCarga, lblNota);
        return box;
    }

    // --- AÇÕES DE NAVEGAÇÃO ---

    @FXML
    public void handleVoltar(ActionEvent event) {
        // Método antigo, mantido por compatibilidade
        Navegacao.navegar(event, "/modulosigaa/view/BuscaPorAluno.fxml", "Consultar Alunos");
    }

    @FXML
    public void handleVoltar2(ActionEvent event) {
        // Botão "Voltar" principal da tela nova
        Navegacao.navegar(event, "/modulosigaa/view/BuscaPorAluno.fxml", "Consultar Alunos");
    }
    
    @FXML
    public void handleImprimir() {
        System.out.println("Enviando grade para impressão...");
        // Aqui você implementaria a lógica de gerar PDF se necessário
    }
    
    // --- STUBS PARA O MENU (Evita erros se clicar no menu superior) ---
    @FXML public void handleVisualizarGradeCurricular() {}
    @FXML public void handleDisciplinasEspeciais() {}
    @FXML public void handleListarPendenciasCriticas() {}
    @FXML public void handleVisualizarConsultasAgendadas() {}
}