package modulosigaa.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modulosigaa.db.DBConnection;
import modulosigaa.model.entity.Disciplina;
import modulosigaa.model.entity.SituacaoDisciplina;
import modulosigaa.model.enums.StatusSituacao;

public class SituacaoDisciplinaRepository {

    // Busca o histórico completo de um aluno específico
    public List<SituacaoDisciplina> buscarPorAluno(int matricula) {
        List<SituacaoDisciplina> lista = new ArrayList<>();
        
        // SQL com JOIN para buscar o nome, período e carga horária da disciplina
        // Isso evita ter que fazer uma consulta extra para cada matéria
        String sql = "SELECT sd.*, d.nomeDisciplina, d.periodo, d.CargaHoraria " +
                     "FROM Situacao_Disciplina sd " +
                     "JOIN Disciplina d ON sd.FK_idDisciplina = d.idDisciplina " +
                     "WHERE sd.FK_matriculaAluno = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SituacaoDisciplina sd = new SituacaoDisciplina();
                sd.setMatriculaAluno(rs.getInt("FK_matriculaAluno"));
                sd.setIdDisciplina(rs.getInt("FK_idDisciplina"));
                
                // Converte a String do banco ("Aprovado", "Cursando") para o Enum StatusSituacao
                String statusStr = rs.getString("statusDisciplina");
                sd.setStatusDisciplina(StatusSituacao.fromString(statusStr));
                
                sd.setNota(rs.getDouble("nota"));

                // Preenche o objeto 'Disciplina' dentro do histórico para uso no Controller
                Disciplina d = new Disciplina();
                d.setIdDisciplina(sd.getIdDisciplina());
                d.setNome(rs.getString("nomeDisciplina"));
                d.setPeriodo(rs.getInt("periodo"));
                d.setCargaHoraria(rs.getInt("CargaHoraria"));
                
                sd.setDisciplinaDetalhada(d);
                lista.add(sd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao buscar histórico: " + e.getMessage());
        }
        return lista;
    }
}