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

    public List<SituacaoDisciplina> buscarPorAluno(int matricula) {
        List<SituacaoDisciplina> lista = new ArrayList<>();
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
                

                String statusStr = rs.getString("statusDisciplina");
                sd.setStatusDisciplina(StatusSituacao.fromString(statusStr));
                
                sd.setNota(rs.getDouble("nota"));

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
        }
        return lista;
    }
}