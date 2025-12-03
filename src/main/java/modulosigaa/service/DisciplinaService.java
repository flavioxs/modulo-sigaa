package modulosigaa.service;

import modulosigaa.repository.DisciplinaRepository;
import modulosigaa.model.entity.Disciplina;

import java.util.List;

public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository; 

    public DisciplinaService() {
        this.disciplinaRepository = new DisciplinaRepository(); 
    }

    public List<Disciplina> listarDisciplinasPorGrade(int idGrade) {
        return disciplinaRepository.buscarPorGrade(idGrade);
    }

    public List<Disciplina> listarTodasDisciplinas() {
        return disciplinaRepository.listarTodas();
    }

    public Disciplina buscarDisciplinaPorId(int id) {
        return disciplinaRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada com ID: " + id));
    }
}