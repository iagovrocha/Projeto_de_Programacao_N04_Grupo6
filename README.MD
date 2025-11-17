# Projeto de Programação - N04 - GRUPO 06 - Notificação
## Objetivo do módulo desenvolvido (Módulo Notificação)
* Criar classes
* Implementar envio automático de mensagens (confirmações, lembretes,
cancelamentos)
* Impedir conflitos de notificações
* **Implementar fila de mensagens para evitar sobrecarga (notificações em
lote)**
* **Permitir histórico de notificações enviadas e recebidas por usuário**
* **Adicionar controle de status (enviado, lido, não lido)**
* Criar repositório para persistir dados
    * **Criar as tabelas notificacao e mensagem**
    * **Implementar o repositório NotificacaoRepository com operações CRUD**
    * **Criar método para registrar o envio de notificações (data, tipo e destinatário)**
    * **Integrar com módulos de pagamento e eventos (por exemplo: pagamento confirmado → notificação enviada)**
* Criar repositório e interface visual para visualização das notificações. **(Frontend)**
* Testar (Criação de Teste Unitários)
## Bibliotecas utilizadas
* Spring Boot - 3.5.6
    * Spring Boot Dev Tools
    * Spring Web
    * Spring Data JPA
    * MySQL Driver
    * Validation 
    * Lombok
* Java 21
## Instruções de execução
Executar classe AppApplication.java no pacote br.com.unit.app
## Mudanças
1. Decidimos utilizar /app ao invés de /src
2. As mudanças da semana 1 estão nas branchs de cada colaborador do grupo.
3. Decidimos fazer isso devido a mudança do projeto para Spring Boot na semana 2.
4. Mudanças referentes a semana 3. Estão na branch semana 3.

## Responsabilidades de cada integrante

## Prints ou exemplos de saída
