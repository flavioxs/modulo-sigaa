package modulosigaa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modulosigaa.model.entity.Professor;
import modulosigaa.model.entity.SolicitacaoDisciplina;
import modulosigaa.repository.ProfessorRepository;
import modulosigaa.service.SolicitacaoService;
import modulosigaa.utils.Navegacao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DisciplinasEspeciaisController {

    @FXML private VBox disciplinasVBox; // Container da lista no FXML

    private final SolicitacaoService solicitacaoService = new SolicitacaoService();
    private final ProfessorRepository professorRepository = new ProfessorRepository();

    @FXML
    public void initialize() {
        carregarSolicitacoesAgrupadas();
    }

    private void carregarSolicitacoesAgrupadas() {
        disciplinasVBox.getChildren().clear();
        disciplinasVBox.setSpacing(15);

        // 1. Busca todas as pendências
        List<SolicitacaoDisciplina> todasPendencias = solicitacaoService.listarSolicitacoesPendentes();

        if (todasPendencias.isEmpty()) {
            Label vazio = new Label("Não há solicitações pendentes no momento.");
            vazio.setStyle("-fx-font-size: 14px; -fx-text-fill: #777;");
            disciplinasVBox.getChildren().add(vazio);
            return;
        }

        // 2. Agrupa por Nome da Disciplina (Java Streams)
        // Cria um mapa onde a Chave é o Nome da Matéria e o Valor é a lista de alunos que pediram ela
        Map<String, List<SolicitacaoDisciplina>> agrupamento = todasPendencias.stream()
                .collect(Collectors.groupingBy(SolicitacaoDisciplina::getNomeDisciplina));

        // 3. Cria um card para cada Disciplina (Chave do mapa)
        for (String nomeDisciplina : agrupamento.keySet()) {
            List<SolicitacaoDisciplina> pedidosDaMateria = agrupamento.get(nomeDisciplina);
            disciplinasVBox.getChildren().add(criarCardDisciplina(nomeDisciplina, pedidosDaMateria));
        }
    }

    // Cria o cartão visual da MATÉRIA
    private VBox criarCardDisciplina(String nomeDisciplina, List<SolicitacaoDisciplina> pedidos) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1);");

        // --- Cabeçalho ---
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label lblDisciplina = new Label(nomeDisciplina);
        lblDisciplina.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        lblDisciplina.setStyle("-fx-text-fill: #3f5ad8;");

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Contador de solicitantes
        Label lblContador = new Label(pedidos.size() + " solicitantes");
        lblContador.setStyle("-fx-background-color: #fff3cd; -fx-text-fill: #856404; -fx-padding: 5 10; -fx-background-radius: 15; -fx-font-weight: bold;");

        header.getChildren().addAll(lblDisciplina, spacer, lblContador);

        // --- Botões de Ação da Matéria ---
        HBox actions = new HBox(15);
        actions.setAlignment(Pos.CENTER_LEFT);

        // Botão 1: Verificar Professores
        Button btnProfessores = new Button("👨‍🏫 Verificar Professores");
        btnProfessores.setStyle("-fx-background-color: #e3f2fd; -fx-text-fill: #0d47a1; -fx-cursor: hand; -fx-border-color: #bbdefb;");
        // Pega a área da primeira solicitação da lista (todas da mesma matéria tem a mesma área)
        String area = pedidos.get(0).getAreaConhecimento();
        btnProfessores.setOnAction(e -> abrirJanelaProfessores(nomeDisciplina, area));

        // Botão 2: Ver Justificativas (e Aceitar/Recusar)
        Button btnJustificativas = new Button("📝 Ver Justificativas (" + pedidos.size() + ")");
        btnJustificativas.setStyle("-fx-background-color: #3f5ad8; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;");
        btnJustificativas.setOnAction(e -> abrirJanelaJustificativas(nomeDisciplina, pedidos));

        actions.getChildren().addAll(btnJustificativas, btnProfessores);

        card.getChildren().addAll(header, new Separator(), actions);
        return card;
    }

    // --- JANELA DE PROFESSORES ---
    private void abrirJanelaProfessores(String disciplina, String area) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Disponibilidade Docente");
        alert.setHeaderText("Professores qualificados para: " + disciplina + "\nÁrea: " + area);

        // Busca professores no banco
        List<Professor> professores = professorRepository.buscarPorArea(area);

        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        
        if (professores.isEmpty()) {
            content.getChildren().add(new Label("Nenhum professor encontrado para a área: " + area));
        } else {
            for (Professor p : professores) {
                VBox pCard = new VBox(3);
                pCard.setStyle("-fx-border-color: #eee; -fx-padding: 5; -fx-background-color: #f9f9f9;");
                
                Label lblNome = new Label(p.getNome());
                lblNome.setStyle("-fx-font-weight: bold;");
                Label lblEmail = new Label("📧 " + p.getEmailInstitucional());
                Label lblArea = new Label("📚 " + p.getAreaAtuacao());
                lblArea.setStyle("-fx-text-fill: #666; -fx-font-size: 10px;");
                
                pCard.getChildren().addAll(lblNome, lblEmail, lblArea);
                content.getChildren().add(pCard);
            }
        }

        ScrollPane scroll = new ScrollPane(content);
        scroll.setPrefHeight(300);
        scroll.setPrefWidth(400);
        
        alert.getDialogPane().setContent(scroll);
        alert.showAndWait();
    }

    // --- JANELA DE JUSTIFICATIVAS (ONDE ACEITA/RECUSA) ---
    private void abrirJanelaJustificativas(String disciplina, List<SolicitacaoDisciplina> pedidos) {
        Alert alert = new Alert(Alert.AlertType.NONE); // Janela customizada
        alert.setTitle("Análise de Solicitações");
        alert.setHeaderText("Alunos solicitando: " + disciplina);
        
        // Botão Fechar padrão
        alert.getButtonTypes().add(javafx.scene.control.ButtonType.CLOSE);

        VBox content = new VBox(10);
        content.setPadding(new Insets(10));

        for (SolicitacaoDisciplina sol : pedidos) {
            VBox alunoCard = new VBox(5);
            alunoCard.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 10; -fx-background-color: #fff;");

            // Dados do Aluno
            Label lblAluno = new Label(sol.getNomeAluno() + " (" + sol.getMatriculaAluno() + ")");
            lblAluno.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
            
            Label lblJust = new Label("Justificativa:");
            lblJust.setStyle("-fx-font-size: 10px; -fx-text-fill: #777;");
            
            TextArea txtJustificativa = new TextArea(sol.getJustificativa());
            txtJustificativa.setEditable(false);
            txtJustificativa.setWrapText(true);
            txtJustificativa.setPrefHeight(60);
            txtJustificativa.setStyle("-fx-control-inner-background: #f4f4f4;");

            // Botões de Decisão
            HBox botoes = new HBox(10);
            botoes.setAlignment(Pos.CENTER_RIGHT);
            
            Button btnAceitar = new Button("Aceitar");
            btnAceitar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
            
            Button btnRecusar = new Button("Recusar");
            btnRecusar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");

            // Ação dos botões
            btnAceitar.setOnAction(e -> {
                processarDecisao(sol, true);
                alert.close(); // Fecha janela para forçar refresh
            });
            
            btnRecusar.setOnAction(e -> {
                processarDecisao(sol, false);
                alert.close();
            });

            botoes.getChildren().addAll(btnRecusar, btnAceitar);
            alunoCard.getChildren().addAll(lblAluno, lblJust, txtJustificativa, botoes);
            content.getChildren().add(alunoCard);
        }

        ScrollPane scroll = new ScrollPane(content);
        scroll.setPrefHeight(400);
        scroll.setPrefWidth(500);
        
        alert.getDialogPane().setContent(scroll);
        alert.showAndWait();
        
        // Ao fechar a janela, recarrega a tela principal para atualizar contadores
        carregarSolicitacoesAgrupadas();
    }

    private void processarDecisao(SolicitacaoDisciplina sol, boolean aprovado) {
        try {
            if (aprovado) {
                solicitacaoService.aprovarSolicitacao(sol);
            } else {
                solicitacaoService.recusarSolicitacao(sol);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    public void handleVoltar(ActionEvent event) {
        Navegacao.navegar(event, "/modulosigaa/view/TelaInicial.fxml", "Portal do Orientador");
    }
}