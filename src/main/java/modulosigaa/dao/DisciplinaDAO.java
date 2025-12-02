package modulosigaa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modulosigaa.db.DBConnection;
import modulosigaa.model.entity.Disciplina;

public class DisciplinaDAO {

    public List<Disciplina> listarTodas() {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT * FROM Disciplina ORDER BY nomeDisciplina";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                disciplinas.add(mapearDisciplina(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar disciplinas: " + e.getMessage(), e);
        }
        return disciplinas;
    }

    public List<Disciplina> buscarPorGrade(int idGrade) {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT d.* FROM Disciplina d " +
                     "JOIN Grade_Composicao gc ON d.idDisciplina = gc.FK_idDisciplina " +
                     "WHERE gc.FK_idGrade = ? " +
                     "ORDER BY d.periodo, d.nomeDisciplina";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idGrade);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                disciplinas.add(mapearDisciplina(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar disciplinas da grade " + idGrade + ": " + e.getMessage(), e);
        }
        return disciplinas;
    }

    public Optional<Disciplina> buscarPorId(int idDisciplina) {
        String sql = "SELECT * FROM Disciplina WHERE idDisciplina = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDisciplina);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearDisciplina(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar disciplina por ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    private Disciplina mapearDisciplina(ResultSet rs) throws SQLException {
        Disciplina disciplina = new Disciplina();
        disciplina.setIdDisciplina(rs.getInt("idDisciplina"));
        disciplina.setNome(rs.getString("nomeDisciplina"));
        disciplina.setAreaConhecimento(rs.getString("areaConhecimento"));
        disciplina.setCargaHoraria(rs.getInt("CargaHoraria"));
        disciplina.setPeriodo(rs.getInt("periodo"));
        disciplina.setObrigatoria(rs.getBoolean("obrigatoriedade"));
        return disciplina;
    }
}