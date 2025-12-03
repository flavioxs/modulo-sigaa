package modulosigaa.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import modulosigaa.db.DBConnection;
import modulosigaa.model.entity.Atendimento;
import modulosigaa.model.enums.StatusAtendimento;

public class AtendimentoRepository {
    
    public List<AtendimentoDTO> buscarPorOrientador(int idOrientador) {
        List<AtendimentoDTO> lista = new ArrayList<>();
        String sql = "SELECT at.*, a.nomeAluno " +
                     "FROM Atendimento at " +
                     "JOIN Aluno a ON at.FK_matriculaAluno = a.matricula " +
                     "WHERE at.FK_idOrientador = ? " +
                     "ORDER BY at.dataAtendimento DESC, at.horarioAtendimento ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idOrientador);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Atendimento at = new Atendimento();
                at.setIdAtendimento(rs.getInt("idAtendimento"));
                at.setDataAtendimento(rs.getDate("dataAtendimento").toLocalDate());
                
                Time time = rs.getTime("horarioAtendimento");
                if (time != null) {
                    at.setHorarioAtendimento(time.toLocalTime());
                }
                
                at.setStatusAtendimento(StatusAtendimento.fromString(rs.getString("statusAtendimento")));
                at.setMatriculaAluno(rs.getInt("FK_matriculaAluno"));
                at.setIdOrientador(rs.getInt("FK_idOrientador"));

                String nomeAluno = rs.getString("nomeAluno");
                lista.add(new AtendimentoDTO(at, nomeAluno));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void inserir(Atendimento at) {
        String sql = "INSERT INTO Atendimento (dataAtendimento, statusAtendimento, horarioAtendimento, FK_matriculaAluno, FK_idOrientador) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(at.getDataAtendimento()));
            stmt.setString(2, at.getStatusAtendimento().getDescricaoBanco());
            stmt.setTime(3, Time.valueOf(at.getHorarioAtendimento()));
            stmt.setInt(4, at.getMatriculaAluno());
            stmt.setInt(5, at.getIdOrientador());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao agendar atendimento: " + e.getMessage());
        }
    }

    public void atualizarStatus(int idAtendimento, StatusAtendimento novoStatus) {
        String sql = "UPDATE Atendimento SET statusAtendimento = ? WHERE idAtendimento = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, novoStatus.getDescricaoBanco());
            stmt.setInt(2, idAtendimento);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static class AtendimentoDTO {
        private final Atendimento atendimento;
        private final String nomeAluno;

        public AtendimentoDTO(Atendimento atendimento, String nomeAluno) {
            this.atendimento = atendimento;
            this.nomeAluno = nomeAluno;
        }

        public Atendimento getAtendimento() { return atendimento; }
        public String getNomeAluno() { return nomeAluno; }
    }
}