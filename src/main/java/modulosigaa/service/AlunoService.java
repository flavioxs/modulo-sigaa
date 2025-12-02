package modulosigaa.service;

import modulosigaa.model.entity.Aluno;
import modulosigaa.repository.AlunoRepository;
import java.util.List;

public class AlunoService {
    private final AlunoRepository alunoRepository;

    public AlunoService() {
        this.alunoRepository = new AlunoRepository();
    }

    public List<Aluno> listarTodosAlunos() {
        return alunoRepository.buscarTodos();
    }

    public List<Aluno> listarAlunosCriticos() {
        return alunoRepository.buscarCriticos();
    }
}