package modulosigaa.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulosigaa.db.DBConnection;
import modulosigaa.model.entity.SolicitacaoDisciplina;
import modulosigaa.model.enums.StatusSolicitacao;

public class SolicitacaoDisciplinaRepository {

    public void criar(SolicitacaoDisciplina solicitacao) {
        String sql = "INSERT INTO Solicitacao_Disciplina (statusSolicitacao, justificativa, dataSolicitacao, FK_matriculaAluno, FK_idDisciplina) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, solicitacao.getStatusSolicitacao().getDescricaoBanco());
            stmt.setString(2, solicitacao.getJustificativa());
            stmt.setDate(3, Date.valueOf(solicitacao.getDataSolicitacao()));
            stmt.setInt(4, solicitacao.getMatriculaAluno());
            stmt.setInt(5, solicitacao.getIdDisciplina());

            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    solicitacao.setIdSolicitacao(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar solicitação: " + e.getMessage());
        }
    }

    public void atualizarStatus(int idSolicitacao, StatusSolicitacao novoStatus) {
        String sql = "UPDATE Solicitacao_Disciplina SET statusSolicitacao = ? WHERE idSolicitacao = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, novoStatus.getDescricaoBanco());
            stmt.setInt(2, idSolicitacao);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status: " + e.getMessage());
        }
    }

    public List<SolicitacaoDisciplina> listarPendentes() {
        List<SolicitacaoDisciplina> lista = new ArrayList<>();
        
        String sql = "SELECT s.*, a.nomeAluno, d.nomeDisciplina, d.areaConhecimento " +
                     "FROM Solicitacao_Disciplina s " +
                     "JOIN Aluno a ON s.FK_matriculaAluno = a.matricula " +
                     "JOIN Disciplina d ON s.FK_idDisciplina = d.idDisciplina " +
                     "WHERE s.statusSolicitacao = 'Pendente'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearSolicitacao(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pendências: " + e.getMessage());
        }
        return lista;
    }

    private SolicitacaoDisciplina mapearSolicitacao(ResultSet rs) throws SQLException {
        SolicitacaoDisciplina sd = new SolicitacaoDisciplina();
        sd.setIdSolicitacao(rs.getInt("idSolicitacao"));
        sd.setStatusSolicitacao(StatusSolicitacao.fromString(rs.getString("statusSolicitacao")));
        sd.setJustificativa(rs.getString("justificativa"));
        sd.setDataSolicitacao(rs.getDate("dataSolicitacao").toLocalDate());
        sd.setMatriculaAluno(rs.getInt("FK_matriculaAluno"));
        sd.setIdDisciplina(rs.getInt("FK_idDisciplina"));
        
        // Dados extras
        sd.setNomeAluno(rs.getString("nomeAluno"));
        sd.setNomeDisciplina(rs.getString("nomeDisciplina"));
        
        // ESTA LINHA ESTAVA FALTANDO OU VINDO NULA:
        sd.setAreaConhecimento(rs.getString("areaConhecimento")); 
        
        return sd;
    }
}