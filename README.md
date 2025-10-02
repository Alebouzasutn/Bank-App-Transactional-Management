# Bank-App-Transactional-Management
BankApp is a sample banking application built with Java 17, Spring Boot 3, Spring Security with JWT, and MySQL. It allows users to manage accounts, credit cards, and transactions securely, with validations and integration to a simulated payment gateway. Es una app bancaria de ejemplo con validaciones de seguridad y simulacion de pago.
📌 BankApp – Aplicación Bancaria con Spring Boot

(English version below ⬇️)

📖 Descripción

BankApp es una aplicación bancaria de ejemplo desarrollada con Java 17, Spring Boot 3, Spring Security con JWT y MySQL.
Permite manejar usuarios, tarjetas de crédito y transacciones de manera segura, con validaciones e integración a un cliente de pago simulado.

🏗️ Estructura del Proyecto
🔐 Seguridad

SecurityConfig → Configura Spring Security y registra el filtro JWT.

JwtProvider → Genera y valida tokens JWT.

JwtFilter → Intercepta requests y valida el token.

UserDetailsServiceImpl → Carga usuarios desde DB para la autenticación.

👤 Usuarios y Autenticación

User → Entidad que representa un cliente del banco.

UserRepository → Acceso a la base de datos de usuarios.

AuthController → Endpoints /api/auth/register y /api/auth/login.

💳 Tarjetas

Card → Entidad que representa una tarjeta de crédito.

CardRepository → Acceso a la DB de tarjetas.

CardController → Endpoints para registrar y listar tarjetas.

💰 Transacciones

Transaction → Entidad que representa una operación (compra, pago, etc.).

TransactionRepository → Persistencia de transacciones.

TransactionService → Lógica de negocio (validaciones, saldo, integración con PaymentGatewayClient).

TransactionController → Endpoints para registrar transacciones y consultar historial.

🧪 Testing

TransactionServiceTest → Pruebas unitarias con JUnit 5 + Mockito.

⚙️ Flujo de interacción

El usuario se registra o inicia sesión (AuthController).

Obtiene un JWT generado por JwtProvider.

Para acceder a endpoints protegidos, el token se valida en JwtFilter.

El usuario puede registrar tarjetas (CardController).

Puede realizar transacciones (TransactionController) → TransactionService valida y registra.

Se invoca al PaymentGatewayClient simulado.

🚀 Ejecución

Clonar repo

git clone https://github.com/tu-usuario/bankapp.git
cd bankapp


Configurar MySQL

CREATE DATABASE bankapp;


Editar application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/bankapp
spring.datasource.username=root
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
jwt.secret=claveSuperSecreta
jwt.expiration=3600000


Ejecutar

mvn spring-boot:run


Probar con Postman o curl

📌 BankApp – Banking Application with Spring Boot
📖 Description

BankApp is a sample banking application built with Java 17, Spring Boot 3, Spring Security with JWT, and MySQL.
It allows users to manage accounts, credit cards, and transactions securely, with validations and integration to a simulated payment gateway.

🏗️ Project Structure
🔐 Security

SecurityConfig → Configures Spring Security and registers the JWT filter.

JwtProvider → Generates and validates JWT tokens.

JwtFilter → Intercepts requests and validates JWTs.

UserDetailsServiceImpl → Loads users from DB for authentication.

👤 Users & Authentication

User → Entity representing a bank client.

UserRepository → Database access for users.

AuthController → Endpoints /api/auth/register and /api/auth/login.

💳 Cards

Card → Entity representing a credit card.

CardRepository → Database access for cards.

CardController → Endpoints to register and list cards.

💰 Transactions

Transaction → Entity representing a financial operation.

TransactionRepository → Transaction persistence.

TransactionService → Business logic (validations, balance, integration with PaymentGatewayClient).

TransactionController → Endpoints to create transactions and query history.

🧪 Testing

TransactionServiceTest → Unit tests with JUnit 5 + Mockito.

⚙️ Flow of Interaction

User registers or logs in via AuthController.

A JWT is issued by JwtProvider.

Protected endpoints require JWT validation by JwtFilter.

User can register cards via CardController.

User can perform transactions via TransactionController.

Business rules run in TransactionService, which calls PaymentGatewayClient.

🚀 How to Run

Clone repo

git clone https://github.com/your-username/bankapp.git
cd bankapp


Setup MySQL

CREATE DATABASE bankapp;


Edit application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/bankapp
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
jwt.secret=superSecretKey
jwt.expiration=3600000


Run

mvn spring-boot:run


Test with Postman or curl
