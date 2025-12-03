package modulosigaa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modulosigaa.model.entity.Aluno;
import modulosigaa.service.AlunoService;
import modulosigaa.utils.Navegacao;
import modulosigaa.utils.SessaoUsuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BuscaPorAlunoController {

    @FXML private TextField nomeInput;
    @FXML private TextField matriculaInput;
    @FXML private VBox alunoListVBox; 

    private final AlunoService alunoService = new AlunoService();
    private List<Aluno> listaCompletaAlunos = new ArrayList<>(); 

    @FXML
    public void initialize() {
        carregarDadosIniciais();
        configurarFiltroDinamico();
    }

    private void carregarDadosIniciais() {
        try {
            listaCompletaAlunos = alunoService.listarTodosAlunos();
            if (listaCompletaAlunos == null) listaCompletaAlunos = new ArrayList<>();
            atualizarListaVisual(listaCompletaAlunos);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar alunos: " + e.getMessage());
        }
    }

    private void configurarFiltroDinamico() {
        nomeInput.textProperty().addListener((observable, oldValue, newValue) -> filtrarAlunos());
        matriculaInput.textProperty().addListener((observable, oldValue, newValue) -> filtrarAlunos());
    }

    private void filtrarAlunos() {
        String filtroNome = nomeInput.getText().toLowerCase().trim();
        String filtroMatricula = matriculaInput.getText().trim();

        List<Aluno> alunosFiltrados = listaCompletaAlunos.stream()
                .filter(aluno -> {
                    boolean matchNome = aluno.getNome().toLowerCase().contains(filtroNome);
                    boolean matchMatricula = String.valueOf(aluno.getMatricula()).contains(filtroMatricula);
                    return matchNome && matchMatricula;
                })
                .collect(Collectors.toList());

        atualizarListaVisual(alunosFiltrados);
    }

    private void atualizarListaVisual(List<Aluno> alunos) {
        alunoListVBox.getChildren().clear(); 
        for (Aluno aluno : alunos) {
            alunoListVBox.getChildren().add(criarCardAluno(aluno));
        }
    }

    private HBox criarCardAluno(Aluno aluno) {
        HBox card = new HBox();
        card.setAlignment(Pos.CENTER_LEFT);
        card.setSpacing(15);
        card.setPadding(new Insets(10, 15, 10, 15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-border-color: #e0e0e0; -fx-border-radius: 5;");
        
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.1));
        shadow.setRadius(2);
        shadow.setOffsetY(1);
        card.setEffect(shadow);

        Label faixaRisco = new Label();
        faixaRisco.setMinWidth(5);
        faixaRisco.setPrefWidth(5);
        faixaRisco.setPrefHeight(40);
        String corRisco = switch (aluno.getStatusRisco()) {
            case CRITICO -> "#e53935";
            case ATENCAO -> "#fb8c00";
            default -> "#43a047";   
        };
        faixaRisco.setStyle("-fx-background-color: " + corRisco + "; -fx-background-radius: 2;");

        VBox infoBox = new VBox(3);
        Label lblNome = new Label(aluno.getNome());
        lblNome.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        lblNome.setTextFill(Color.web("#333333"));

        Label lblMatricula = new Label("Matrícula: " + aluno.getMatricula() + " | IRA: " + aluno.getIra());
        lblMatricula.setFont(Font.font("Verdana", 12));
        lblMatricula.setTextFill(Color.web("#777777"));

        infoBox.getChildren().addAll(lblNome, lblMatricula);

        HBox.setHgrow(infoBox, Priority.ALWAYS);

        Button btnVisualizar = new Button("Visualizar");
        btnVisualizar.setStyle("-fx-background-color: #3f5ad8; -fx-text-fill: white; -fx-cursor: hand;");
        
        btnVisualizar.setOnAction(e -> {
            SessaoUsuario.getInstance().setAlunoSelecionado(aluno);
            Navegacao.navegar(e, "/modulosigaa/view/VisualizarGradeAcademicaPesquisa.fxml", "Grade Curricular - " + aluno.getNome());
        });

        card.getChildren().addAll(faixaRisco, infoBox, btnVisualizar);
        
        return card;
    }

    @FXML
    public void handlePesquisarAluno(ActionEvent event) {
        filtrarAlunos();
    }

    @FXML
    public void handleVoltar(ActionEvent event) {
        Navegacao.navegar(event, "/modulosigaa/view/TelaInicial.fxml", "Portal do Orientador");
    }
}