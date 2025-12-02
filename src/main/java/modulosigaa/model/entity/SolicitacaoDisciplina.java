package modulosigaa.model.entity;

import java.time.LocalDate;
import modulosigaa.model.enums.StatusSolicitacao;

public class SolicitacaoDisciplina {
    private int idSolicitacao;
    private StatusSolicitacao statusSolicitacao;
    private String justificativa;
    private LocalDate dataSolicitacao;
    private int matriculaAluno;
    private int idDisciplina;
    private String nomeDisciplina; 
    private String areaConhecimento; 
    private String nomeAluno;

    public SolicitacaoDisciplina() {}


    public String getAreaConhecimento() { return areaConhecimento; }
    public void setAreaConhecimento(String areaConhecimento) { this.areaConhecimento = areaConhecimento; }
    
    public int getIdSolicitacao() { return idSolicitacao; }
    public void setIdSolicitacao(int idSolicitacao) { this.idSolicitacao = idSolicitacao; }
    
    public StatusSolicitacao getStatusSolicitacao() { return statusSolicitacao; }
    public void setStatusSolicitacao(StatusSolicitacao statusSolicitacao) { this.statusSolicitacao = statusSolicitacao; }
    
    public String getJustificativa() { return justificativa; }
    public void setJustificativa(String justificativa) { this.justificativa = justificativa; }
    
    public LocalDate getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDate dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }
    
    public int getMatriculaAluno() { return matriculaAluno; }
    public void setMatriculaAluno(int matriculaAluno) { this.matriculaAluno = matriculaAluno; }
    
    public int getIdDisciplina() { return idDisciplina; }
    public void setIdDisciplina(int idDisciplina) { this.idDisciplina = idDisciplina; }
    
    public String getNomeDisciplina() { return nomeDisciplina; }
    public void setNomeDisciplina(String nomeDisciplina) { this.nomeDisciplina = nomeDisciplina; }
    
    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }
}