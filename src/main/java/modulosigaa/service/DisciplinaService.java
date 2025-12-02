package modulosigaa.service;

import modulosigaa.dao.DisciplinaDAO;
import modulosigaa.model.entity.Disciplina;

import java.util.List;

public class DisciplinaService {

    private final DisciplinaDAO disciplinaDAO;

    public DisciplinaService() {
        this.disciplinaDAO = new DisciplinaDAO();
    }

    /**
     * Retorna a lista de disciplinas de uma grade curricular específica.
     * Ideal para exibir as opções de matrícula ou solicitação especial.
     */
    public List<Disciplina> listarDisciplinasPorGrade(int idGrade) {
        // Regra de Negócio (exemplo): Poderíamos validar se o idGrade existe antes de chamar o DAO.
        return disciplinaDAO.buscarPorGrade(idGrade);
    }

    /**
     * Retorna todas as disciplinas da instituição.
     * Útil para buscas gerais ou administrativas.
     */
    public List<Disciplina> listarTodasDisciplinas() {
        return disciplinaDAO.listarTodas();
    }

    /**
     * Busca detalhes de uma disciplina.
     */
    public Disciplina buscarDisciplinaPorId(int id) {
        return disciplinaDAO.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada com ID: " + id));
    }
}