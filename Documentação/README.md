# Distribuidora Ferreira - Guia de Execução Local

## Executando o Sistema Backend

### 1. Pré-requisitos:

1. **Java Development Kit (JDK)**:
   - Certifique-se de ter o JDK instalado em sua máquina. Você pode baixá-lo em [Oracle JDK Downloads](https://www.oracle.com/java/technologies/downloads/).

2. **Apache Maven**:
   - Instale o Apache Maven para gerenciar as dependências do projeto. Você pode baixá-lo em [Apache Maven Downloads](https://maven.apache.org/download.cgi).

3. **PostgreSQL**:
   - Instale o banco de dados PostgreSQL em sua máquina. Você pode baixá-lo em [PostgreSQL Downloads](https://www.postgresql.org/download/).

### 2. Configuração do Banco de Dados:

1. **Crie um banco de dados**:
   - Crie um novo banco de dados no PostgreSQL com o nome que desejar (por exemplo, `distribuidora_ferreira`).

2. **Configure as credenciais**:
   - Configure as credenciais de acesso ao banco de dados (usuário, senha e host) no arquivo `application.properties`.

### 3. Clonagem do Repositório:

Clone o repositório do projeto da Distribuidora Ferreira em sua máquina:
```bash
git clone  https://github.com/ICEI-PUC-Minas-PMGES-TI/pmg-es-2024-1-ti3-9577100-distribuidora-ferreira.git
```

### 4. Execução da Aplicação:
Navegue até o diretório do projeto clonado:
```bash
cd pmg-es-2024-1-ti3-9577100-distribuidora-ferreira
```
Execute a aplicação com o comando:
```bash
mvn spring-boot:run
```
### 5. Acesso à API:
Após a aplicação iniciar, a API estará disponível em sua máquina.
Você pode acessar os endpoints da API através de um navegador web ou ferramentas de teste como Postman.

## Executando o Sistema Frontend
### 1. Pré-requisitos:
1. **Instale o Node.js e o npm:**
  - Se você ainda não tem o Node.js e o npm instalados, baixe e instale-os a partir do site oficial: ([Node.js Downloads](https://nodejs.org/en/download/package-manager/](https://nodejs.org/en/download/package-manager)).
2. **Instale o Angular CLI:**
    - Abra um terminal (prompt de comando) e execute o seguinte comando:
    ```bash
    npm install -g @angular/cli
    ```
Clone o repositório do projeto da Distribuidora Ferreira em sua máquina:
```bash
git clone  https://github.com/ICEI-PUC-Minas-PMGES-TI/pmg-es-2024-1-ti3-9577100-distribuidora-ferreira.git
```

### 4. Execução da Aplicação:
Navegue até o diretório do projeto clonado:
```bash
cd pmg-es-2024-1-ti3-9577100-distribuidora-ferreira
```
Execute a aplicação com o comando:
```bash
ng serve
```
