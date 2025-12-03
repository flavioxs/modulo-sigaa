````markdown
# 🎓 Módulo de Apoio ao Orientador Acadêmico - SIGAA (UERN)

## 📄 Sobre o Projeto

Este sistema é um módulo desktop desenvolvido em **JavaFX** que visa auxiliar **Orientadores Acadêmicos** no acompanhamento de discentes.  
Ele atua como uma extensão das funcionalidades do SIGAA, oferecendo uma interface visual para gestão de pendências, grades curriculares e agendamentos.

---

## 🏗️ Arquitetura

O projeto segue a arquitetura **BCE (Boundary-Control-Entity)**:

- **Entity (Modelo):** Classes POJO que espelham tabelas do banco (`Aluno`, `Disciplina`, etc.).
- **Boundary (Repositório):** Acesso a dados via **JDBC puro**.
- **Control (Service):** Regras de negócio e validações.
- **View (JavaFX):** Interface gráfica construídas com FXML + Controllers.

---

## ✨ Funcionalidades Principais

### 🔍 1. Consulta de Alunos e Grade Visual
- Busca alunos por nome ou matrícula.
- **Mapa visual da grade curricular** organizado por semestre.
- Códigos de cores:
  - 🟢 **Verde:** Aprovado
  - 🔵 **Azul:** Matriculado/Cursando
  - 🔴 **Vermelho:** Reprovado/Pendente

### 📋 2. Gestão de Disciplinas Especiais
- Listagem de solicitações de quebra de pré-requisito.
- Visualização de justificativa e disponibilidade docente por área.
- Ações de **Deferir** e **Indeferir**.

### 📅 3. Agenda de Atendimentos
- Painel de agendamentos.
- Criação de novos atendimentos.
- Histórico de orientações realizadas.

### ⚠️ 4. Monitoramento de Risco
- Identificação automática de alunos em situação de **risco de jubilamento** ou monitoramento.

---

## 🚀 Como Executar o Projeto

### ✔️ Pré-requisitos
- **Java JDK 21** ou superior  
- **Maven** instalado  
- **MySQL Server** rodando na porta **3306**

---

### 🔧 Passo 1: Configurar o Banco de Dados

1. Abra seu gerenciador MySQL (Workbench, DBeaver, etc.).
2. Execute o script na raiz do projeto:

📌 `BD_SistemaAuxilioOrientador.sql`

Esse script irá criar as tabelas e inserir dados de teste.

---

### 📥 Passo 2: Clonar e Configurar o Projeto

```bash
git clone https://github.com/seu-usuario/modulo-sigaa.git
cd modulo-sigaa
````

---

### 🔐 Passo 3: Configurar Credenciais (se necessário)

O sistema usa por padrão `root` / `root`.
Se sua senha for outra, edite:

📌 `src/main/java/modulosigaa/db/DBConnection.java`

```java
public class DBConnection {
    private static final String URL  = "jdbc:mysql://localhost:3306/db_sigaa";
    private static final String USER = "root"; // altere aqui
    private static final String PASS = "root"; // altere aqui
}
```

---

### ▶️ Passo 4: Rodar a Aplicação

No terminal, dentro do diretório do projeto:

```bash
mvn clean javafx:run
```

---

## 👥 Autores

* **Eduardo Milhomes Barbosa de Medeiros**
* **Flávio de Aguiar Xavier Filho**
* **Juliana Assis de Arimateia Silva**
* **Lucas Bezerra de Lima**
* **Mateus Gomes Neri**

---
