package modulosigaa.service;

import java.time.LocalDate;
import java.util.List;

import modulosigaa.repository.SolicitacaoDisciplinaRepository; 
import modulosigaa.model.entity.Aluno;
import modulosigaa.model.entity.Disciplina;
import modulosigaa.model.entity.SolicitacaoDisciplina;
import modulosigaa.model.enums.StatusSolicitacao;

public class SolicitacaoService {

    private final SolicitacaoDisciplinaRepository repository; 

    public SolicitacaoService() {
        this.repository = new SolicitacaoDisciplinaRepository(); 
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

        repository.criar(solicitacao);
    }

    public List<SolicitacaoDisciplina> listarSolicitacoesPendentes() {
        return repository.listarPendentes();
    }

    public void aprovarSolicitacao(SolicitacaoDisciplina solicitacao) {
        repository.atualizarStatus(solicitacao.getIdSolicitacao(), StatusSolicitacao.ACEITA);
    }

    public void recusarSolicitacao(SolicitacaoDisciplina solicitacao) {
        repository.atualizarStatus(solicitacao.getIdSolicitacao(), StatusSolicitacao.RECUSADA);
    }
}