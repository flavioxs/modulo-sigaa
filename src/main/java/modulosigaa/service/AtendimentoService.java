package modulosigaa.service;

import java.util.List;
import modulosigaa.model.entity.Atendimento;
import modulosigaa.model.enums.StatusAtendimento;
import modulosigaa.repository.AtendimentoRepository;

public class AtendimentoService {

    private final AtendimentoRepository repository;

    public AtendimentoService() {
        this.repository = new AtendimentoRepository();
    }

    public List<AtendimentoRepository.AtendimentoDTO> listarAtendimentosDoOrientador(int idOrientador) {
        return repository.buscarPorOrientador(idOrientador);
    }

    public void agendarAtendimento(Atendimento atendimento) {
        if (atendimento.getStatusAtendimento() == null) {
            atendimento.setStatusAtendimento(StatusAtendimento.CONFIRMADO);
        }
        repository.inserir(atendimento);
    }

    public void confirmarAtendimento(int idAtendimento) {
        repository.atualizarStatus(idAtendimento, StatusAtendimento.CONFIRMADO);
    }

    public void cancelarAtendimento(int idAtendimento) {
        repository.atualizarStatus(idAtendimento, StatusAtendimento.RECUSADO);
    }
}