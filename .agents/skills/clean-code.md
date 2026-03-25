# Boas Práticas de Clean Code para Agentes Antigravity

Estas são as "skills" fundamentais ("Clean Code" e "Boas Práticas") que **todo** Agente IA envolvido neste projeto (Full-Stack) deve sempre respeitar e sugerir ao usuário nos códigos gerados:

## Princípios Gerais (Toda a Arquitetura)
1. **Nomes Claros e Intuitivos**: Variáveis, classes e métodos devem revelar sua intenção sem a necessidade de comentários ambíguos. Prefira nomes em inglês e evite abreviações obscuras.
2. **Funções Pequenas (SRP)**: Uma função ou método deve fazer apenas *uma* coisa. Mantenha os corpos das funções o menores possível (geralmente sob 20-30 linhas).
3. **DRY (Don't Repeat Yourself)**: Lógicas idênticas devem ser extraídas para utilitários, abstrações ou componentes compartilhados globais.
4. **Tratamento de Exceções**: Retorne erros úteis, em um formato padrão (`JSON` na API). Nunca oculte (swallow) exceções sem logá-las.
5. **Legibilidade vs. "Esperteza"**: Evite otimizações prematuras que tornam o código ilegível. Priorize código simples e fácil de dar manutenção.

## Dicas por Stack
- **Frontend**: Componentes desacoplados, uso maduro de Hooks, isolamento de CSS (módulos, Tailwind) sem dependências globais desnecessárias.
- **Backend (Java/Spring)**: Consumo via injeção de dependências, uso de Enums, separação estrita entre Controllers, Services e Repositories.
- **Banco/DBA**: Índices adequadamente mapeados, nomes de tabelas/colunas consistentes (`snake_case`), sem N+1 nas `queries`.

*Qualquer agente que gerar código no Lab Cana Fire DEVE seguir estas orientações.*
