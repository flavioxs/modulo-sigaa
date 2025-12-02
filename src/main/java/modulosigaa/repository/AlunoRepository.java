package modulosigaa.repository;

import modulosigaa.db.DBConnection;
import modulosigaa.model.entity.Aluno;
import modulosigaa.model.enums.StatusRisco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoRepository {

    public List<Aluno> buscarTodos() {
        return buscarComFiltro(null);
    }

    public List<Aluno> buscarCriticos() {
        String sql = "SELECT * FROM Aluno WHERE statusRisco IN ('Risco', 'Monitoramento')";
        return executarQuery(sql);
    }

    private List<Aluno> buscarComFiltro(String condicaoExtra) {
        String sql = "SELECT * FROM Aluno";
        if (condicaoExtra != null && !condicaoExtra.isEmpty()) {
            sql += " " + condicaoExtra;
        }
        return executarQuery(sql);
    }

    private List<Aluno> executarQuery(String sql) {
        List<Aluno> lista = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Aluno a = new Aluno();
                a.setMatricula(rs.getInt("matricula"));
                a.setNome(rs.getString("nomeAluno"));
                a.setIra(rs.getDouble("IRA"));
                a.setEmailInstitucional(rs.getString("A_emailInstitucional"));
                a.setStatusRisco(StatusRisco.fromString(rs.getString("statusRisco")));
                a.setIdOrientador(rs.getInt("FK_idOrientador"));
                a.setIdProfessor(rs.getInt("FK_idProfessor"));
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar alunos: " + e.getMessage());
        }
        return lista;
    }
}