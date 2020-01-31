# Integração com Apache Kafka – Integrations & Development Tools
Produtor na linguagem Python que efetua a carga do arquivo do bolsa família em um tópico Kafka através.
Três consumidores em Java que efetuam as seguintes análises:
1) Exibir [UF] + [somar da parcela] + [qtd de beneficiários] até o momento; 
2) Mostrar dados do beneficiário que tenha a maior parcela até o momento;
3) Quantidade de registros lidos do Topic.

![G1 - Microserviços Trab2](https://user-images.githubusercontent.com/50683744/73410249-7322f600-42e0-11ea-8bf5-475e4159a7b9.jpg)

## Instalação

A instalação é feita executando os containeres das aplicações. Após clonar o repositório do git, basta executar o docker com o comando abaixo:

    docker-compose up

Após a execução do docker-compose, a aplicação já deve estar em execução. É criado automaticamente um tópico Kafka no qual serão carregados os registros.

## Exemplo de uso

#### Producer

Para carregar registros no tópico, deve-se acessar o endpoint criado pela aplicação Producer:

http://localhost:5000/iniciar?arquivo=nomeDoArquivo.csv&&linhas=qtdLinhas

Onde:
- nomeDoArquivo.csv é o nome do arquivo que será utilizado para leitura dos registros do bolsa família
- qtdLinhas é a quantidade de linhas do arquivo que deverá ser processada pelo Producer

Exemplos de utilização:

*Executa com arquivo de testes. Sem limite de linhas (processa o arquivo inteiro)*

http://localhost:5000/iniciar?arquivo=Teste.csv

*Executa com arquivo original. Limita em 2000 linhas*

http://localhost:5000/iniciar?arquivo=201901_BolsaFamilia_Pagamentos.csv&&linhas=2000

#### Consumer

Para consumir registros no tópico, deve-se acessar os endpoints criados pelas aplicações Consumer:

*Dados dos beneficiários por UF, somando as parcelas*

http://localhost:8080/report

*Dados do beneficiário com a maior parcela*

http://localhost:8081/comparator

*Quantidade de registros lidos no tópico*

http://localhost:8082/stats


O resultado deverá ser mostrado no console da aplicação no docker.

Exemplo da demonstração dos resultados:
Consumer 01: Relatório com o totais de beneficiários e valores por UF:
![Consumer 01](https://user-images.githubusercontent.com/2822029/73510644-9a052900-43c1-11ea-8de8-1be3b0121fc5.png)

## Configuração para Desenvolvimento

%%%%%%%%%%%%%%%%% exemplo %%%%%%%%%%%%%%%%%%%%%%%%%

Descreva como instalar todas as dependências para desenvolvimento e como rodar um test-suite automatizado de algum tipo. Se necessário, faça isso para múltiplas plataformas.

make install
npm test

%%%%%%%%%%%%%%%%% exemplo %%%%%%%%%%%%%%%%%%%%%%%%%

## Histórico de lançamentos
0.0.1 - Versão inicial atendendo os requisitos do trabalho

## Lista de atividades necessárias para implementação inicial

Screenshot do Trello com as atividades necessárias para implementação dos consumidores e produtor.

![trello](https://user-images.githubusercontent.com/50683744/73412033-1e827980-42e6-11ea-9ccc-5311242cd2aa.PNG)


## Contribuições
Contribuições são bem-vindas, fiquem à vontade 😉

Faça o fork do projeto; crie uma branch; dê o commit; faça o push e o pull request. 

Não esqueçam de garantir o correto funcionamento da aplicação conforme seu objetivo. 
