package modulosigaa.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import modulosigaa.model.enums.StatusAtendimento;

public class Atendimento {
    private int idAtendimento;
    private LocalDate dataAtendimento;
    private LocalTime horarioAtendimento;
    private StatusAtendimento statusAtendimento;
    private int matriculaAluno;
    private int idOrientador;

    public Atendimento() {}

    public int getIdAtendimento() { return idAtendimento; }
    public void setIdAtendimento(int idAtendimento) { this.idAtendimento = idAtendimento; }

    public LocalDate getDataAtendimento() { return dataAtendimento; }
    public void setDataAtendimento(LocalDate dataAtendimento) { this.dataAtendimento = dataAtendimento; }

    public LocalTime getHorarioAtendimento() { return horarioAtendimento; }
    public void setHorarioAtendimento(LocalTime horarioAtendimento) { this.horarioAtendimento = horarioAtendimento; }

    public StatusAtendimento getStatusAtendimento() { return statusAtendimento; }
    public void setStatusAtendimento(StatusAtendimento statusAtendimento) { this.statusAtendimento = statusAtendimento; }

    public int getMatriculaAluno() { return matriculaAluno; }
    public void setMatriculaAluno(int matriculaAluno) { this.matriculaAluno = matriculaAluno; }

    public int getIdOrientador() { return idOrientador; }
    public void setIdOrientador(int idOrientador) { this.idOrientador = idOrientador; }
}