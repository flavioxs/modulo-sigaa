package modulosigaa.dao;

import modulosigaa.db.DBConnection;
import modulosigaa.model.entity.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    public List<Professor> listarTodos() {
        List<Professor> professores = new ArrayList<>();
        String sql = "SELECT * FROM Professor ORDER BY nomeProfessor";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Professor p = new Professor();
                p.setIdProfessor(rs.getInt("idProfessor"));
                p.setNome(rs.getString("nomeProfessor"));
                p.setEmailInstitucional(rs.getString("P_emailInstitucional"));
                p.setAreaAtuacao(rs.getString("areaAtuacao"));
                professores.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar professores: " + e.getMessage());
        }
        return professores;
    }
}