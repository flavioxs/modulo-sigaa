package modulosigaa.service;

import java.time.LocalDate;
import java.util.List;

import modulosigaa.dao.SolicitacaoDisciplinaDAO;
import modulosigaa.model.entity.Aluno;
import modulosigaa.model.entity.Disciplina;
import modulosigaa.model.entity.SolicitacaoDisciplina;
import modulosigaa.model.enums.StatusSolicitacao;

public class SolicitacaoService {

    private final SolicitacaoDisciplinaDAO dao;

    public SolicitacaoService() {
        this.dao = new SolicitacaoDisciplinaDAO();
    }

    public void solicitarDisciplina(Aluno aluno, Disciplina disciplina, String justificativa) {
        if (justificativa == null || justificativa.trim().length() < 10) {
            throw new IllegalArgumentException("A justificativa deve ter pelo menos 10 caracteres.");
        }

        SolicitacaoDisciplina solicitacao = new SolicitacaoDisciplina();
        solicitacao.setMatriculaAluno(aluno.getMatricula());
        solicitacao.setIdDisciplina(disciplina.getIdDisciplina());
        solicitacao.setJustificativa(justificativa);
        solicitacao.setStatusSolicitacao(StatusSolicitacao.PENDENTE);
        solicitacao.setDataSolicitacao(LocalDate.now());

        dao.criar(solicitacao);
    }

    public List<SolicitacaoDisciplina> listarSolicitacoesPendentes() {
        return dao.listarPendentes();
    }

    public void aprovarSolicitacao(SolicitacaoDisciplina solicitacao) {
        dao.atualizarStatus(solicitacao.getIdSolicitacao(), StatusSolicitacao.ACEITA);
    }

    public void recusarSolicitacao(SolicitacaoDisciplina solicitacao) {
        dao.atualizarStatus(solicitacao.getIdSolicitacao(), StatusSolicitacao.RECUSADA);
    }
}