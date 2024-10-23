<h1> PetHouse </h1>

<h3 align="center">
    Projeto PetShop PetHouse 🐶
    <br>
    <br><br>
    <p align="center">
      <a href="#sobre">Sobre</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
      <a href="#back">Backend</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
      <a href="#execB">Execução do projeto Backend</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
      <a href="#execF">Execução do projeto Frontend</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
      <a href="#tec">Tecnologias</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
      <a href="#contato">Entre em contato</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  </p>
</h3>

<hr>

<h3 id="sobre"> Sobre </h3>

Este projeto é uma aplicação Full Stack para um E-commerce de PetShop, desenvolvida com Java, Spring Boot 3+ no backend e Angular no frontend. O objetivo é simular uma plataforma completa onde os usuários podem navegar e realizar compras de produtos para pets de maneira intuitiva e segura.

<hr>

<h3 id="back"> Backend 🔧 </h3>

<h2> Arquitetura de Microsservices PetHouse 🧱 </h2>

![Screenshot_1](https://github.com/user-attachments/assets/a63a563b-566e-483f-9260-2d60f310157e)

> Esta aplicação utiliza uma arquitetura baseada em microsserviços para gerenciar diferentes funcionalidades de forma isolada, garantindo escalabilidade, modularidade e facilidade de manutenção. Abaixo estão os principais microsserviços que compõem o sistema.

#### Microsservice PetHouse 🏠

__Descrição:__

Este é o serviço principal da aplicação, responsável por gerenciar grande parte das funcionalidades, incluindo o cadastro de usuários, gerenciamento de produtos, controle do carrinho de compras e envio de mensagens para os tópicos no Kafka.

#### Microsservice Auth 🔐

__Descrição:__

Este microsserviço é responsável pela autenticação e autorização dos usuários. Ele gerencia a criação de tokens JWT para controle de sessão e também a renovação de tokens.

#### Microsservice Payment 💸

__Descrição:__

Este serviço é responsável por processar os pagamentos dos pedidos feitos pelos usuários. Ele ouve o tópico Kafka Itens para receber detalhes do pedido e então integra com o sistema de pagamentos Assas para completar a transação.

#### Microsservice Email 📧

__Descrição:__

Este microsserviço é responsável pelo envio de e-mails personalizados aos usuários, utilizando templates específicos para diferentes eventos. Ele ouve vários tópicos Kafka, como bemVindo, carrinho e vendas, e aciona o envio de e-mails adequados.

<hr>

<h3> Fluxo entre Microsserviços 💫</h3>

> O fluxo de dados entre os microsserviços foi projetado para garantir uma interação eficiente entre as funcionalidades da aplicação, utilizando tópicos no Kafka para comunicação assíncrona entre os serviços. Esse fluxo garante que os microsserviços possam operar de forma desacoplada, mas ainda assim colaborativa.

- 1. __Cadastro de Usuários__
Quando um novo usuário se cadastra na aplicação através do PetHouse Service, os dados do usuário são enviados ao tópico Kafka bemVindo. O Email Service escuta este tópico e envia automaticamente um e-mail de boas-vindas ao usuário.

- 2. __Adição de Produtos ao Carrinho__
Quando o usuário adiciona um produto ao carrinho, o PetHouse Service envia os detalhes do carrinho para o tópico Kafka carrinho. O Email Service escuta este tópico e pode enviar um e-mail de notificação ao usuário com um resumo do carrinho ou lembretes sobre os itens.

- 3. __Seleção de Método de Pagamento__
Após o usuário escolher um método de pagamento, o PetHouse Service envia os dados do pedido para o tópico Kafka Itens. O Payment Service escuta este tópico para processar o pagamento utilizando a integração com o sistema Assas.

- 4. __Processamento do Pagamento__
O Payment Service processa o pagamento e, uma vez que o pedido é confirmado e o pagamento é aprovado, ele envia uma mensagem para o tópico Kafka vendas. Isso aciona tanto o PetHouse Service para atualizar o status do pedido quanto o Email Service para enviar um e-mail de confirmação de compra ao usuário.

#### __Resumo do Fluxo:__

__Cadastro de Usuário:__

__Ação:__ O usuário se cadastra no PetShop.
__Resultado:__ O PetHouse Service envia os dados ao tópico bemVindo, acionando o envio de um e-mail de boas-vindas pelo Email Service.

__Ação no Carrinho:__

__Ação:__ O usuário adiciona produtos ao carrinho.
__Resultado:__ O PetHouse Service envia os dados ao tópico carrinho, acionando o Email Service para enviar e-mails de lembrete.

__Escolha do Pagamento:__

__Ação:__ O usuário seleciona o método de pagamento.
__Resultado:__ O PetHouse Service envia os dados para o tópico Itens, que é escutado pelo Payment Service para processar o pagamento.

__Confirmação de Pagamento:__

__Ação:__ O pagamento é processado com sucesso.

__Resultado:__ O Payment Service envia os dados para o tópico vendas, notificando o Email Service para enviar a confirmação de compra e o PetHouse Service para atualizar o status do pedido.

![o](https://github.com/user-attachments/assets/cc1ba038-c084-4a44-b59d-dae69de395c8)

<hr>

<h3> Documentação centralizada com Swagger 📗 </h3>

> Centralizamos a documentação do nosso sistema usando Swagger, proporcionando uma visão unificada e acessível de todos os nossos serviços

![Frame_2](https://github.com/user-attachments/assets/13f7da92-5aef-4e6c-b0b9-b9ec4108afb1)

https://github.com/user-attachments/assets/d8ec03dc-51a0-4141-ab9b-61a685b13c88

__Acesse a documentação do projeto:__

> Quando subir a aplicação acesse usando esse link

 - __Acesse:__ http://localhost:8080/swagger-ui.html para visualizar os endpoints.

<hr>

<h3> Distributed Tracing com ZIPKIN 👁️‍🗨️ </h3>

> Implementação Distributed Tracing no sistema utilizando o Zipkin, uma ferramenta poderosa para monitorar e analisar o fluxo de solicitações em ambientes distribuídos.

![Frame_122](https://github.com/user-attachments/assets/3d41dbc2-1ba7-4277-8b76-bb4dcecb9084)

https://github.com/user-attachments/assets/0908a0be-559b-4b2c-bf47-3e1e7639d1ec

__Acesse a documentação do projeto:__

> Quando subir a aplicação acesse usando esse link

 - __Acesse:__ http://localhost:9411/zipkin/ para visualizar o Zipkin.

<hr>

<h3> Migration com Flyway ️‍️✈️ </h3>

> Utilizei o Flyway para gerenciar e versionar as migrações do banco de dados de forma automática e eficiente. Isso garante que o esquema do banco esteja sempre atualizado, permitindo controle de versão e facilidade na aplicação de novas alterações.

![flyway](https://github.com/user-attachments/assets/0cffa7a8-48f2-4b0c-b53e-0ecbbb19bf0b)

<hr>

<h3> Upload AWS S3 ☁️ </h3>

> As imagens dos produtos são armazenadas no AWS S3, garantindo um armazenamento seguro, escalável e altamente disponível. O S3 permite que os arquivos de imagens sejam facilmente acessados pela aplicação, proporcionando performance e integridade dos dados.

![Screenshot_2](https://github.com/user-attachments/assets/a36af56b-6e6a-46a8-907a-b260e23eca56)

<hr>

<h3> Integração com Assas para Pagamentos 💳 </h3>

> A aplicação está integrada ao Assas, um sistema de gestão de pagamentos que permite processar transações de forma segura e eficiente. Com essa integração, é possível oferecer várias opções de pagamento, como PIX, boleto bancário, e cartão de crédito.

![Screenshot_3](https://github.com/user-attachments/assets/799dae89-6dba-4563-b620-a6cf965bbf56)

<hr>

<h3> Pipeline CI/CD com Jenkins e Push para DockerHub 🐳 </h3>

> A integração contínua e o deploy contínuo (CI/CD) do projeto são gerenciados pelo Jenkins. Esse pipeline é responsável por automatizar o processo de construção da aplicação, empacotamento da imagem Docker, e envio para o DockerHub.

![Screenshot_4](https://github.com/user-attachments/assets/b14e4c4b-e61b-4569-91e4-17e4a16460e4)

<h3 id="execB"> Execução do projeto Backend 🤓 </h3>

## Execute o projeto 👁‍🗨

__Pré-requisitos:__ Java 17 e Docker

__Clone o repositório do projeto__

~~~~~~Bash
git clone https://github.com/AugustoMello09/PetHouse.git
~~~~~~

### Configurando o projeto local 🏠

__Configurando o ambiente:__

- Navegue até o diretório do projeto

~~~~~~Bash
cd PetHouse/PetHouseBackend
~~~~~~

- Acesse todos os diretórios do projeto, utilize o comando `cd` e o nome do diretório para instalar todas as dependências necessárias:

~~~~~~Bash
# exemplo
cd PetHouseBackend/auth

mvn clean package -DskipTest=true
~~~~~~

- Acesse o diretório de cada microsserviço (por exemplo, PetHouse, payment).
Abra o arquivo de configuração application.yml.

- Modifique as configurações necessárias, como URLs dos serviços dependentes.

> Troque para localhost

~~~~~~yml
management:
  tracing:
    sampling:
      probability: 1.0
  zipkin:
    tracing:
      endpoint: http://localhost:9411/api/v2/spans

kafka:
  consumer:
    bootstrap-servers: localhost:29092
  producer:
    bootstrap-servers: localhost:29092
~~~~~~

<hr>

##### Modificando as variáveis de ambiente

__AWS S3__

 - Criação de uma conta da AWS

  `https://aws.amazon.com/pt/`

 - Criando um bucket no S3

  1 - No console AWS, acessar S3

  2 - Create Bucket -> dê um nome e desmarque a opção de Block all public access

 - Setup do IAM - Identity Access Management

 Acessar o dashboard -> Security, Identity & Compliance -> IAM

 - Setup do MFA - Multi-factor authentication (OPCIONAL)

  1 - Instalar o Google Authenticator App no seu smartphone

  2 - Clicar no botão "Manage MFA" e selecione "A virtual MFA device"

  3 - Ler o QR Code a partir do app Google Authenticator

  4 - Entrar com dois códigos gerados pelo app e clique em "Activate Virtual MFA"

 - Create individual IAM users

  1 - Manage users -> Create new users

  2 - Crie um usuário para seu sistema acessar o S3 (exemplo: "spring_user")

  3 - Baixe o arquivo com as credenciais do usuário (user name, access key id, secret access key)

 - Use groups to assign permissions

  1 - Manage groups -> Create new group

  2 - Criar um grupo (exemplo: "developers")

  3 - Busque por "S3" e selecione "AmazonS3FullAccess" e confirme

  4 - Selecione o grupo e clique: Group Actions -> Add Users do Group

  5 - Selecione o usuário e confirme

- Apply an IAM password policy (OPCIONAL)

  1 - Manage Password Policy

  2 - Selecione as políticas desejadas

- Alterações na políticas de Block do bucket

~~~JSON
{
    "Version": "2008-10-17",
    "Statement": [
      {
        "Sid": "AllowPublicRead",
        "Effect": "Allow",
        "Principal": {
        "AWS": "*"
        },
        "Action": [
        "s3:GetObject"
        ],
        "Resource": [
        "arn:aws:s3:::curso-spring-ionic/*"
        ]
      }
   ]
}
~~~

__Alterando os dados do application.yml do PetHouse__

> agora com os dados necessários crie as variáveis de ambiente para conseguir usar os serviço da AWS S3

 - Depois de ter salvo as variáveis de ambiente entra na PetHouse e coloque os valores das chaves

 ~~~yml
 aws:
   s3:
     access-key: ${AWS_ACCESS_KEY}
     secret-key: ${AWS_SECRET_ACCESS_KEY}
     region: ${AWS_REGION}
     bucket-name: ${AWS_BUCKET_NAME}
~~~

__Assas__

> Agora Crie uma conta no ambiente de SendBox do Assas e gere o token de acesso.

`https://docs.asaas.com/docs/visao-geral`

um vídeo explicativo no canal do Assas `https://youtu.be/3TEclkugpkE`

- Com o token criado faça a mesma coisa e crie uma nova variavel de ambiente e coloque o valor no yml da PetHouse e também do Payment

 ~~~yml
 access:
   token: ${access_token}
~~~

<hr>

##### Rodando a aplicação

> Antes de rodar prepare o docker-compose

~~~yml
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    networks:
      - broker-kafka
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  kafka:
    image: confluentinc/cp-kafka:latest
    networks:
      - broker-kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    healthcheck:
      test: ["CMD", "kafka-broker-api-versions", "--bootstrap-server", "localhost:9092"]
      interval: 10s
      retries: 5
      timeout: 10s
      start_period: 20s

  kafdrop:
    image: obsidiandynamics/kafdrop:latest
    networks:
      - broker-kafka
    depends_on:
      kafka:
        condition: service_healthy
    ports:
      - "19000:9000"
    environment:
      KAFKA_BROKERCONNECT: kafka:29092

  zipkin-service:
    image: openzipkin/zipkin:latest
    container_name: zipkin-service
    ports:
      - 9411:9411
    healthcheck:
      test: ["CMD", "wget", "--spider", "-S", "http://zipkin-service:9411/"]
      interval: 5s
      retries: 5
      timeout: 10s
      start_period: 0s
    depends_on:
      kafka:
        condition: service_healthy
    networks:
      - broker-kafka

networks:
  broker-kafka:
    driver: bridge
~~~

- Primeiro Suba o docker com o comando `docker-compose up -d`

- Suba os microsservices na seguinte ordem:

`euraka, gateway, auth, PetHouse, payment e email` com o container rodando.

~~~~Bash
# Exemplo para o PetHouse

cd PetHouseBackend/PetHouse

mvn spring-boot:run
~~~~

### Configurando o projeto para usar Docker-compose 🐳

__Clone o repositório do projeto__

~~~~~~Git
git clone https://github.com/AugustoMello09/PetHouse.git
~~~~~~

- Navegue até o diretório do projeto

~~~~~~Bash
cd PetHouse/PetHouseBackend
~~~~~~

- Navegue até o diretório de cada projeto e troque o `localhost` pelo nome do service que está no docker-compose

~~~~~~yml
management:
  tracing:
    sampling:
      probability: 1.0
  zipkin:
    tracing:
      endpoint: http://zipkin-service:9411/api/v2/spans

      kafka:
         consumer:
           bootstrap-servers: kafka:29092
         producer:
           bootstrap-servers: kafka:29092
~~~~~~

- Agora com os dados gerados anteriormente entre em cada diretório, faça build e Build da docker-img

~~~~~~Bash
# exemplo

cd PetHouse/PetHouseBackend

cd eureka

mvn clean package -DskipTest=true

docker build -t seu usuário do DockerHub/pethouse-backend-eureka .

docker push seu usuário do DockerHub/pethouse-backend-eureka:latest
~~~~~~

- Configure o docker-compose com sua img

~~~~~~yml
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    networks:
      - broker-kafka
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  kafka:
    image: confluentinc/cp-kafka:latest
    networks:
      - broker-kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    healthcheck:
      test: ["CMD", "kafka-broker-api-versions", "--bootstrap-server", "localhost:9092"]
      interval: 10s
      retries: 5
      timeout: 10s
      start_period: 20s

  kafdrop:
    image: obsidiandynamics/kafdrop:latest
    networks:
      - broker-kafka
    depends_on:
      kafka:
        condition: service_healthy
    ports:
      - "19000:9000"
    environment:
      KAFKA_BROKERCONNECT: kafka:29092

  zipkin-service:
    image: openzipkin/zipkin:latest
    container_name: zipkin-service
    ports:
      - 9411:9411
    healthcheck:
      test: ["CMD", "wget", "--spider", "-S", "http://zipkin-service:9411/"]
      interval: 5s
      retries: 5
      timeout: 10s
      start_period: 0s
    depends_on:
      kafka:
        condition: service_healthy
    networks:
      - broker-kafka

  eureka-service:
    build:
      dockerfile: ./eureka/Dockerfile
      context: .
    image: seu usuário/pethouse-backend-eureka:latest
    container_name: eureka-service
    ports:
      - 8761:8761
    networks:
      - broker-kafka

  gateway-service:
    build:
      dockerfile: ./gateway/Dockerfile
      context: .
    image: seu usuário/pethouse-backend-gateway:latest
    container_name: gateway-service
    environment:
      - eureka.client.fetch-registry = true
      - eureka.client.register-with-eureka = true
      - eureka.client.serviceUrl.defaultZone = http://eureka-service:8761/eureka
      - spring.zipkin.base-url = http://zipkin-service:9411/api/v2/spans
    ports:
      - 8080:8080
    networks:
      - broker-kafka
    depends_on:
      eureka-service:
        condition: service_started
      zipkin-service:
        condition: service_healthy

  auth-service:
    build:
      dockerfile: ./auth/Dockerfile
      context: .
    image: seu usuário/pethouse-backend-auth:latest
    container_name: auth-service
    environment:
      - eureka.client.service-url.defaultZone = http://eureka-service:8761/eureka
      - spring.zipkin.base-url = http://zipkin-service:9411/api/v2/spans
    networks:
      - broker-kafka
    depends_on:
      eureka-service:
        condition: service_started
      zipkin-service:
        condition: service_healthy

  pethouse-service:
    build:
      dockerfile: ./PetHouse/Dockerfile
      context: .
    image: seu usuário/pethouse-backend-pethouse:latest
    container_name: pethouse-service
    environment:
      - spring.kafka.bootstrap-servers=kafka:29092
      - eureka.client.service-url.defaultZone=http://eureka-service:8761/eureka
      - spring.zipkin.base-url=http://zipkin-service:9411/api/v2/spans
    networks:
      - broker-kafka
    depends_on:
      kafka:
        condition: service_healthy
      eureka-service:
        condition: service_started
      zipkin-service:
        condition: service_healthy
      gateway-service:
        condition: service_started

  payment-service:
    build:
      dockerfile: ./payment/Dockerfile
      context: .
    image: seu usuário/pethouse-backend-payment:latest
    container_name: payment-service
    environment:
      - spring.kafka.bootstrap-servers=kafka:29092
      - eureka.client.service-url.defaultZone=http://eureka-service:8761/eureka
      - spring.zipkin.base-url=http://zipkin-service:9411/api/v2/spans
    networks:
      - broker-kafka
    depends_on:
      eureka-service:
        condition: service_started
      zipkin-service:
        condition: service_healthy
      kafka:
        condition: service_healthy
      pethouse-service:
        condition: service_started
      gateway-service:
        condition: service_started

  email-service:
    build:
      dockerfile: ./email/Dockerfile
      context: .
    image: seu usuário/pethouse-backend-email:latest
    container_name: email-service
    environment:
      - spring.kafka.bootstrap-servers=kafka:29092
      - eureka.client.service-url.defaultZone=http://eureka-service:8761/eureka
      - spring.zipkin.base-url=http://zipkin-service:9411/api/v2/spans
    networks:
      - broker-kafka
    depends_on:
      eureka-service:
        condition: service_started
      zipkin-service:
        condition: service_healthy
      kafka:
        condition: service_healthy
      pethouse-service:
        condition: service_started
      payment-service:
        condition: service_started


networks:
  broker-kafka:
    driver: bridge
~~~~~~

<h3 id="execF"> Execução do projeto Frontend 🤪 </h3>

## Execute o projeto 👁

__Pré-requisitos:__ Angular

__Executar__

- Certifique-se de ter o Node.js e o Angular CLI instalados em seu ambiente.
- Navegue até a pasta do projeto front-end:

~~~~~~Bash
cd PetHouse/PethouseFrontend
~~~~~~

__Instale as dependências do projeto:__

~~~~~~Bash
npm install
~~~~~~

<hr>

<h3 id="tec"> Tecnologias </h3>

<div style="display: inline_block"><br>

<img align="center" alt="Augusto-Java" height="70" width="70" src="https://github.com/devicons/devicon/blob/master/icons/java/java-original.svg">
<img align="center" alt="Augusto-SpringBoot" height="70" width="70" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/spring/spring-original-wordmark.svg">
<img align="center" alt="Augusto-POSTGRESQL" height="60" width="60" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/postgresql/postgresql-original-wordmark.svg">
<img align="center" alt="Augusto-Docker" height="70" width="70" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/docker/docker-original.svg">
<img align="center" alt="Augusto-Java" height="40" width="40" src="https://github.com/AugustoMello09/Locadora/assets/101072311/a895137a-8126-4eed-8a5c-9934ed30401b">
‍<img align="center" alt="Augusto-jwt" height="50" width="50" src="https://img.icons8.com/?size=512&id=rHpveptSuwDz&format=png">
<img align="center" alt="Augusto-kafka" height="50" width="50" src="https://github.com/tandpfun/skill-icons/blob/main/icons/Kafka.svg">
<img align="center" alt="Augusto-s3" height="50" width="50" src="https://github.com/weibeld/aws-icons-svg/blob/main/q1-2022/Architecture-Service-Icons_01312022/Arch_Storage/16/Arch_Amazon-Simple-Storage-Service_16.svg">
<img align="center" alt="Augusto-jenkins" height="50" width="50" src="https://github.com/devops-workflow/jenkins-icons/blob/master/icons/jenkins-logo-48x48.png">
<img align="center" alt="Augusto-HTML" height="50" width="50" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/html5/html5-plain.svg">
<img align="center" alt="Augusto-CSS" height="50" width="50" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/css3/css3-original.svg">
<img align="center" alt="Augusto-JAVASCRIP" height="50" width="50" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/javascript/javascript-plain.svg">
<img align="center" alt="Augusto-TYPESCRIPT" height="60" width="60" src="https://img.icons8.com/?size=512&id=nCj4PvnCO0tZ&format=png">
<img align="center" alt="Augusto-ANGULAR" height="50" width="50" src="https://raw.githubusercontent.com/get-icon/geticon/fc0f660daee147afb4a56c64e12bde6486b73e39/icons/angular-icon.svg">

</div>

<hr>

<h3 id="contato"> Entre em contato </h3>

### contato

Para mais informações sobre o projeto ou para entrar em contato, você pode me encontrar através dos canais abaixo:

<div style="display: inline_block">

  <a href="https://www.linkedin.com/in/jos%C3%A9-augusto-mello-794a94234" target="_blank"><img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" target="_blank"></a>
 <a href="mailto:joseaugusto.mello01@gmail.com" target="_blank"><img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white" target="_blank"></a>

</div>
