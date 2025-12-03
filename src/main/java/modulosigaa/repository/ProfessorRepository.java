package modulosigaa.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modulosigaa.db.DBConnection;
import modulosigaa.model.entity.Professor;

public class ProfessorRepository {

    public List<Professor> buscarPorArea(String area) {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Professor WHERE areaAtuacao LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + area + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Professor p = new Professor();
                p.setIdProfessor(rs.getInt("idProfessor"));
                p.setNome(rs.getString("nomeProfessor"));
                p.setEmailInstitucional(rs.getString("P_emailInstitucional"));
                p.setAreaAtuacao(rs.getString("areaAtuacao"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}