package modulosigaa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modulosigaa.model.entity.Aluno;
import modulosigaa.model.entity.Atendimento;
import modulosigaa.model.enums.StatusAtendimento;
import modulosigaa.repository.AtendimentoRepository.AtendimentoDTO;
import modulosigaa.service.AlunoService;
import modulosigaa.service.AtendimentoService;
import modulosigaa.utils.Navegacao;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ConsultasAgendadasController {

    @FXML private VBox agendamentosVBox;

    private final AtendimentoService atendimentoService = new AtendimentoService();
    private final AlunoService alunoService = new AlunoService();
    private final int ID_ORIENTADOR_LOGADO = 2; 

    @FXML
    public void initialize() {
        carregarAtendimentos();
    }

    private void carregarAtendimentos() {
        agendamentosVBox.getChildren().clear();
        agendamentosVBox.setSpacing(15);

        List<AtendimentoDTO> lista = atendimentoService.listarAtendimentosDoOrientador(ID_ORIENTADOR_LOGADO);

        Button btnNovo = new Button("+ Novo Agendamento");
        btnNovo.setStyle("-fx-background-color: #3f5ad8; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnNovo.setMaxWidth(Double.MAX_VALUE);
        btnNovo.setOnAction(e -> abrirDialogoNovoAgendamento());
        
        agendamentosVBox.getChildren().add(btnNovo);

        if (lista.isEmpty()) {
            agendamentosVBox.getChildren().add(new Label("Nenhum atendimento encontrado."));
            return;
        }

        VBox boxPendentes = new VBox(10);
        VBox boxHistorico = new VBox(10);

        for (AtendimentoDTO dto : lista) {
            StatusAtendimento status = dto.getAtendimento().getStatusAtendimento();
            if (status == StatusAtendimento.SOLICITADO) {
                boxPendentes.getChildren().add(criarCardAtendimento(dto, true));
            } else {
                boxHistorico.getChildren().add(criarCardAtendimento(dto, false));
            }
        }

        if (!boxPendentes.getChildren().isEmpty()) {
            Label lbl = new Label("📅 Pendentes de Aprovação");
            lbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #e65100;");
            agendamentosVBox.getChildren().addAll(lbl, boxPendentes);
        }
        
        if (!boxHistorico.getChildren().isEmpty()) {
            Label lbl = new Label("🗂️ Histórico / Confirmados");
            lbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #3f5ad8; -fx-padding: 10 0 0 0;");
            agendamentosVBox.getChildren().addAll(lbl, boxHistorico);
        }
    }

    private HBox criarCardAtendimento(AtendimentoDTO dto, boolean isPendente) {
        HBox card = new HBox(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(15));
        
        String corBorda = isPendente ? "#ff9800" : "#3f5ad8";
        card.setStyle("-fx-background-color: white; -fx-border-color: " + corBorda + "; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-width: 0 0 0 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1);");

        VBox dataBox = new VBox(2);
        dataBox.setAlignment(Pos.CENTER);
        dataBox.setMinWidth(70);
        String dia = dto.getAtendimento().getDataAtendimento().format(DateTimeFormatter.ofPattern("dd/MM"));
        Label lblDia = new Label(dia);
        lblDia.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        Label lblHora = new Label(dto.getAtendimento().getHorarioAtendimento().toString());
        dataBox.getChildren().addAll(lblDia, lblHora);

        VBox infoBox = new VBox(3);
        Label lblNome = new Label(dto.getNomeAluno());
        lblNome.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        Label lblStatus = new Label(dto.getAtendimento().getStatusAtendimento().getDescricaoBanco());
        lblStatus.setStyle("-fx-text-fill: #666;");
        infoBox.getChildren().addAll(lblNome, lblStatus);

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox actions = new HBox(10);
        if (isPendente) {
            Button btnAceitar = new Button("✔ Aceitar");
            btnAceitar.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white;");
            btnAceitar.setOnAction(e -> {
                atendimentoService.confirmarAtendimento(dto.getAtendimento().getIdAtendimento());
                carregarAtendimentos();
            });

            Button btnRecusar = new Button("✖ Recusar");
            btnRecusar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
            btnRecusar.setOnAction(e -> {
                atendimentoService.cancelarAtendimento(dto.getAtendimento().getIdAtendimento());
                carregarAtendimentos();
            });
            actions.getChildren().addAll(btnAceitar, btnRecusar);
        }

        card.getChildren().addAll(dataBox, infoBox, spacer, actions);
        return card;
    }

    private void abrirDialogoNovoAgendamento() {
        Dialog<Atendimento> dialog = new Dialog<>();
        dialog.setTitle("Novo Agendamento");
        dialog.setHeaderText("Agendar atendimento com aluno");

        ButtonType btnConfirmar = new ButtonType("Agendar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnConfirmar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<Aluno> cbAlunos = new ComboBox<>();
        cbAlunos.getItems().addAll(alunoService.listarTodosAlunos());
        
        DatePicker datePicker = new DatePicker();
        TextField txtHora = new TextField();
        txtHora.setPromptText("HH:mm");

        grid.add(new Label("Aluno:"), 0, 0);
        grid.add(cbAlunos, 1, 0);
        grid.add(new Label("Data:"), 0, 1);
        grid.add(datePicker, 1, 1);
        grid.add(new Label("Hora:"), 0, 2);
        grid.add(txtHora, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnConfirmar) {
                try {
                    Atendimento a = new Atendimento();
                    a.setMatriculaAluno(cbAlunos.getValue().getMatricula());
                    a.setDataAtendimento(datePicker.getValue());
                    a.setHorarioAtendimento(LocalTime.parse(txtHora.getText()));
                    a.setIdOrientador(ID_ORIENTADOR_LOGADO);
                    a.setStatusAtendimento(StatusAtendimento.CONFIRMADO); // Já nasce confirmado
                    return a;
                } catch (Exception e) {
                    mostrarAlerta("Erro", "Dados inválidos! Verifique a hora (HH:mm).");
                    return null;
                }
            }
            return null;
        });

        Optional<Atendimento> result = dialog.showAndWait();
        result.ifPresent(at -> {
            try {
                atendimentoService.agendarAtendimento(at);
                carregarAtendimentos();
                mostrarAlerta("Sucesso", "Agendamento realizado!");
            } catch (Exception e) {
                mostrarAlerta("Erro", "Erro ao salvar: " + e.getMessage());
            }
        });
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML public void handleVoltar(ActionEvent event) {
        Navegacao.navegar(event, "/modulosigaa/view/TelaInicial.fxml", "Portal do Orientador");
    }
}