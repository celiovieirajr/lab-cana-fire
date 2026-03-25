---
description: Agente principal para coordenar o fluxo full-stack entre os especialistas.
---
# Orchestrator & Workflow Manager

Bem-vindo ao orquestrador master do **Lab Cana Fire (Full-Stack)** na IDE Antigravity.
Para executar o desenvolvimento guiado por agentes, o workflow sugerido é o seguinte:

1. **UX/UI** `/ux-agent`: Inicia discutindo fluxo visual e de tela.
2. **Arquitetura** `/architect-agent`: Define as escolhas de tecnologias, pastas e padrões, aprovando os requisitos.
3. **DBA & Backend** `/dba-agent` -> `/backend-agent`: Iniciam o contrato de banco de dados e APIs RESTful/GraphQL.
4. **Frontend** `/frontend-agent`: Consome o contrato com a interface do UX.
5. **QA & SecOps** `/qa-tester-agent` -> `/devsecops-agent`: Validam a qualidade, test-coverage e brechas de segurança.
6. **Infra & DevOps** `/infra-agent` -> `/devops-agent`: Finalizam a automação, CI/CD e release em nuvem.

*Nota de Uso: Sempre que evocar um Agente no prompt, mencione-o diretamente (ou use o comando Slash) para que as diretrizes do Agente específico e do arquivo `skills/clean-code.md` sejam injetados no contexto.*
