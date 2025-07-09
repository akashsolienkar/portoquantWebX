```
# portoquantWebx
**PortoQuantWebX** is a Spring Boot–based simulation engine for multi-asset portfolio modeling and risk analysis using Monte Carlo and GARCH techniques. It generates dynamic performance reports in PDF or JSON format, helping investors simulate future outcomes under uncertain market conditions.


# 🚀 Features

-  Monte Carlo simulations (GBM-based) with configurable iterations
-  GARCH(1,1) volatility modeling (planned)
-  Parallelized simulation engine (Fork/Join and Executor)
-  PDF & HTML report generation with pie charts
-  Clean modular domain driven architecture (controller, service, domain, simulation)
-  RESTful APIs with Swagger UI
-  Extensible return/volatility model interfaces



# Tech Stack

 **Language:** Java 17
- **Framework:** Spring Boot 3.x
- **Build Tool:** Maven 
- **Simulation Engine:** Custom GBM + Monte Carlo (multi-threaded)
- **PDF Generator:** OpenHTMLtoPDF
- **Chart Library:** JFreeChart (embedded as base64)
- **Documentation:** SpringDoc OpenAPI / Swagger
- **UI** - CSS / HTML /Thymleaf 
- **Security** - JWT Token based
- **other** - lombok


# Architecture - Domain Driven

|-----------------------------|
|         API Layer    		          | ← Controllers, DTOs
|-----------------------------|
|     Application Layer       	  | ← Coordinates use cases
|-----------------------------|
|       Domain Layer                   | ← Core business logic, rules
|-----------------------------|
|     Infrastructure Layer           | ← DB, APIs, persistence
|-----------------------------|


# APIs Endpoints-

1. User Management
Method   Endpoint                                                   Description
------   --------------------------------------------------         -----------------------------------------------
POST     /api/users/register                                        Register a new user
POST     /api/users/login                                           User login (return JWT or session token)
GET      /api/users/{id}                                            Get user profile
PUT      /api/users/{id}                                            Update user profile
DELETE   /api/users/{id}                                            Delete user account
  
2. Portfolio Management
Method   Endpoint                                                    Description
------   ---------------------------------------------------         -----------------------------------------------
POST     /api/portfolios                                             Create a new portfolio
GET      /api/portfolios                                             Get all portfolios for logged-in user
GET      /api/portfolios/{portfolioId}                               Get details of a specific portfolio
PUT      /api/portfolios/{portfolioId}                               Update portfolio details (name, allocations, etc.)
DELETE   /api/portfolios/{portfolioId}                               Delete a portfolio

3. Asset Management (within a portfolio)
Method   Endpoint                                                    Description
------   ----------------------------------------------------        ------------------------------------------------
POST     /api/portfolios/{portfolioId}/assets                        Add an asset to portfolio (e.g., stocks, bonds)
GET      /api/portfolios/{portfolioId}/assets                        List all assets in a portfolio
GET      /api/portfolios/{portfolioId}/assets/{assetId}              Get details of a specific asset
PUT      /api/portfolios/{portfolioId}/assets/{assetId}              Update asset allocation or parameters
DELETE   /api/portfolios/{portfolioId}/assets/{assetId}              Remove asset from portfolio

4. Simulation Management
Method   Endpoint                                                    Description
------   ------------------------------------------------------      -----------------------------------------------
POST     /api/simulations                                            Run a new simulation with parameters
GET      /api/simulations/{simulationId}                             Retrieve results and metrics for a specific simulation
GET      /api/portfolios/{portfolioId}/simulations                   List all simulations run for a portfolio
GET      /api/simulations/status                                     Check status of long-running simulations

5. Reports
Method   Endpoint                                                    Description
------   ------------------------------------------------            -----------------------------------------------
GET      /api/reports/portfolio/{portfolioId}                        Generate performance & risk reports (PDF or JSON)

📘 6. Health & System
Method   Endpoint                                                    Description
------   ------------------------------------------------            -----------------------------------------------
GET      /actuator/health                                            Spring Boot health endpoint
GET      /api/ping                                                   Lightweight ping check
GET      /api/simulations/status                                     Check status of long-running simulations


# Folder Structure

                               +--------------------+
|----------------------------- |      api/          |
|                              +--------------------+
|                                      |
|            +-------------------------+--------------------------+
|            |                         |                          |
|       controller/                dto/                     mapper/
|
|                              +--------------------+
|----------------------------- |  application/      |
|                              +--------------------+
|                                      |
|                  +------------------+------------------+
|                  |                                     |
|             service/                          service.impl/
|                                                  |
|                                          service.assembler/
|
|                              +--------------------+
|----------------------------- |     domain/        |
|                              +--------------------+
|                                      |
|   +------------+------------+--------------+---------------+--------------+
|   |            |            |              |               |              |
|model/     analytical/   calculations/   simulation/   garchmodel/   model.assets/
|                                     | 
|             +-----------+-----------+------------+-----------+
|             |           |           |            |           |
|         registry/  returnestimations/  volatilityestimations/  
|                                              |
|                            +--------+--------+--------+--------+
|                            |        |        |        |
|                        context/  engine/  runner/  executors/
|
|                              +------------------------+
|----------------------------- |  infrastructure/       |
|                              +------------------------+
|                                      |
|         +-------------+-------------+------------+--------------+
|         |             |                          |              |
|    repository/   historicaldata/             reports/       security/
|                     |                          |               |
|             +-------+-------+          +-------+-------+    +--+--+
|             |       |       |          |       |       |    |     |
|       datasources/ csv/ apiclients/ context/ executor/ models/  auth/
|                                                               |  
|                                                   +----------+----------+
|                                                    |                     |
|                                                charts/               jwt/, user/
|
|                              +---------------------+
|----------------------------  |     config/         |
|                              +---------------------+
|                                      |
|                    SwaggerConfig/, SecurityConfig/, ModelMapperConfig/
|
|                               +---------------------+
|                               |    resources/       |
|                               +---------------------+
|                                       |
|                       +---------------+---------------+
|                       |                               |
|                 templates/                        static/
|                       |
|            reports/portfolio-report.html
|


# Full Folder Structure

portoquant/
└── src/
    └── main/
        ├── java/
        │   └── com/quant/portoquant/
        │       ├── api/
        │       │   ├── controller/
        │       │   │   ├── AssetController.java
        │       │   │   ├── AuthController.java
        │       │   │   ├── HealthController.java
        │       │   │   ├── PortfolioController.java
        │       │   │   ├── ReportController.java
        │       │   │   └── SimulationController.java
        │       │   ├── dto/
        │       │   │   ├── AssetRequest.java
        │       │   │   ├── AssetResponse.java
        │       │   │   ├── HealthRequest.java
        │       │   │   ├── PortfolioRequest.java
        │       │   │   ├── PortfolioResponse.java
        │       │   │   ├── ReportResponse.java
        │       │   │   ├── SimulationRequest.java
        │       │   │   ├── SimulationResponse.java
        │       │   │   ├── SimulationStatusResponse.java
        │       │   │   ├── UserLoginRequest.java
        │       │   │   ├── UserLoginResponse.java
        │       │   │   ├── UserRequest.java
        │       │   │   └── UserResponse.java
        │       │   └── mapper/
        │       │       ├── AssetMapper.java
        │       │       ├── PortfolioMapper.java
        │       │       ├── SimulationMapper.java
        │       │       └── UserMapper.java
        │       ├── application/
        │       │   ├── service/
        │       │   │   ├── AssetService.java
        │       │   │   ├── PortfolioService.java
        │       │   │   ├── ReportService.java
        │       │   │   └── SimulationService.java
        │       │   ├── service/impl/
        │       │   │   ├── AssetServiceImpl.java
        │       │   │   ├── PortfolioServiceImpl.java
        │       │   │   ├── ReportServiceImpl.java
        │       │   │   └── SimulationServiceImpl.java
        │       │   └── service/assembler/
        │       │       └── PortfolioReportAssembler.java
        │       ├── config/
        │       │   ├── ModelMapperConfig.java
        │       │   ├── SecurityConfig.java
        │       │   └── SwaggerConfig.java
        │       ├── domain/
        │       │   ├── analytical/
        │       │   ├── calculations/
        │       │   ├── calculations/registry/
        │       │   ├── calculations/returnestimations/
        │       │   ├── calculations/volatilityestimations/
        │       │   ├── garchmodel/
        │       │   ├── model/
        │       │   ├── model/assets/
        │       │   ├── model/enums/
        │       │   └── simulation/
        │       │       ├── context/
        │       │       ├── engine/
        │       │       ├── executors/
        │       │       ├── model/
        │       │       └── runner/
        │       ├── infrastructure/
        │       │   ├── exception/
        │       │   ├── historicaldata/
        │       │   │   ├── datasources/
        │       │   │   │   ├── apiclients/
        │       │   │   │   ├── csv/
        │       │   │   │   └── parsers/
        │       │   │   └── provider/
        │       │   ├── reports/
        │       │   │   ├── context/
        │       │   │   ├── executor/
        │       │   │   ├── generator/
        │       │   │   └── models/
        │       │   │       └── charts/
        │       │   ├── repository/
        │       │   └── security/
        │       │       ├── auth/
        │       │       ├── jwt/
        │       │       └── user/
        ├── resources/
        │   ├── application.yml
        │   ├── static/
        │   └── templates/
        │       └── reports/
        │           └── portfolio-report.html

```
