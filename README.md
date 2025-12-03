# 🎓 Módulo de Apoio ao Orientador Acadêmico - SIGAA (UERN)

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/javafx-%23FF0000.svg?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/maven-%23C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white)

> Projeto desenvolvido para a disciplina de Análise e Projeto de Sistemas (2025.1) - UERN.

## 📄 Sobre o Projeto

Este sistema é um módulo desktop desenvolvido em **JavaFX** que visa auxiliar **Orientadores Acadêmicos** no acompanhamento de discentes. Ele atua como uma extensão das funcionalidades do SIGAA, oferecendo uma interface visual e intuitiva para gestão de pendências, grades curriculares e agendamentos.

O sistema foca na experiência do orientador, permitindo visualizar graficamente o progresso do aluno e gerenciar solicitações de quebra de pré-requisitos (disciplinas especiais).

## 🏗️ Arquitetura

O projeto segue estritamente o padrão arquitetural **BCE (Boundary-Control-Entity)**:

* **Entity (Modelo):** Classes POJO que espelham o banco de dados (ex: `Aluno`, `Disciplina`).
* **Boundary (Repositório):** Camada responsável pelo acesso ao dados via **JDBC Puro** (sem frameworks ORM).
* **Control (Service):** Camada de regras de negócio e validações.
* **View (JavaFX):** Interface gráfica construída com FXML e Controllers.

---

## ✨ Funcionalidades Principais

### 1. 🔍 Consulta de Alunos e Grade Visual
* Busca dinâmica de alunos por nome ou matrícula.
* **Visualização Gráfica da Grade:** Exibição das disciplinas organizadas por semestres.
* **Código de Cores:**
    * 🟢 **Verde:** Aprovado
    * 🔵 **Azul:** Matriculado/Cursando
    * 🔴 **Vermelho:** Reprovado/Pendente

### 2. 📋 Gestão de Disciplinas Especiais
* Listagem de solicitações de quebra de pré-requisito pendentes.
* **Ferramentas de Análise:**
    * Visualização da justificativa do aluno.
    * Verificação de professores disponíveis na área de conhecimento da disciplina.
* Ação de **Deferir** ou **Indeferir** a solicitação.

### 3. 📅 Agenda de Atendimentos
* Listagem de atendimentos solicitados pelos alunos.
* Confirmação ou Recusa de horários.
* Criação de novos agendamentos diretamente pelo orientador.

### 4. ⚠️ Monitoramento de Risco
* Identificação automática de alunos em situação de "Risco de Jubilamento" ou "Monitoramento".

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
* **Java JDK 21** ou superior.
* **Maven** instalado.
* **MySQL Server** rodando na porta 3306.

### Passo 1: Configurar o Banco de Dados
1.  Abra seu gerenciador MySQL (Workbench, DBeaver, etc.).
2.  Execute o script completo localizado em: `BD_SistemaAuxilioOrientador.sql`.
    * *Isso criará o banco `db_sigaa` e populará com dados de teste.*

### Passo 2: Clonar e Configurar
```bash
git clone [https://github.com/seu-usuario/modulo-sigaa.git](https://github.com/seu-usuario/modulo-sigaa.git)
cd modulo-sigaa

### Passo 3: Configurar Credenciais (Se necessário)
O sistema vem configurado por padrão para usar root / root. Se o seu banco de dados usa uma senha diferente, edite o arquivo src/main/java/modulosigaa/db/DBConnection.java:

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/db_sigaa"; 
    private static final String USER = "root"; // <--- Altere aqui se necessário
    private static final String PASS = "root"; // <--- Altere aqui se necessário
}

### Passo 4: Rodar a Aplicação
No terminal, dentro da pasta do projeto, execute:
mvn clean javafx:run