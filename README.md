
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

**Supported asset types include:**
`Stocks`, `Bonds`, `Cash`, `Crypto`, `Commodities`, `Mutual Funds`, and `Real Estate`.

**Each simulation calculates:**

- **VaR:** Max loss expected under normal conditions.
- **Expected Shortfall:** Average loss in worst-case scenarios.
- **Volatility:** Measures portfolio risk or fluctuations.
- **Mean Return:** Average expected portfolio gain.
- **Bankruptcy Probability:** Chance of losing almost everything.
- **Final Value:** Projected portfolio value at the end.

And generates a final pdf report for your portfolio 


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
- **Other** - lombok
  **Common Design Patterns** - Strategy, Factory, Builder, Template Method, Adapter, Singleton, and Mapper

# Architecture - Domain Driven
```
+-----------------------------+
|        api/                |  --> Web layer (Controllers, DTOs, Mappers)
+-----------------------------+
|        application/        |  --> Service layer (Interfaces, Implementations, Assemblers)
+-----------------------------+
|        domain/             |  --> Core business logic, models, and strategy pattern logic
+-----------------------------+
|        infrastructure/     |  --> Data access, integrations (DB, Redis, JWT, reports, schedulers)
+-----------------------------+
|        config/             |  --> Configuration (Swagger, Security, Redis, Mapper)
+-----------------------------+
|        resources/          |  --> Templates, static files, application.yml
+-----------------------------+

```

# APIs Endpoints-
```
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

```

# Folder Structure
```
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
|         +-------------+-------------+-------------+-------------+
|         |                                           |           |
|    service/                                service.impl/   service.assembler/
|
|                              +--------------------+
|----------------------------- |     domain/        |
|                              +--------------------+
|                                      |
|  +--------+----------+---------+------------+------------+------------+
|  |        |          |         |            |            |            |
|model/ analytical/ calculations/ simulation/ garchmodel/ model.assets/
|                                     |
|         +----------+----------+----------+----------+
|         |          |          |          |          |
|   context/     engine/    runner/   executors/   model/
|     |
| +-------------------+
| | returnestimations/|
| | volatilityestimations/
| | registry/
| +-------------------+
|
|                              +------------------------+
|----------------------------- |  infrastructure/       |
|                              +------------------------+
|                                      |
|      +---------+----------+----------+----------+-----------+----------+
|      |         |                     |          |           |          |
| repository/ historicaldata/      reports/    cache/     scheduler/  security/
|              |                      |                       |         |
|     +--------+--------+     +-------+-------+             +--+--+   +--------+
|     |        |        |     |       |       |             |     |   |        |
|  datasources/ csv/ apiclients/ context/ executor/       rediscache/ auth/ jwt/ user/
|                          |
|                      generator/ 
|                      models/ 
|                          |
|                      charts/
|
|                              +---------------------+
|----------------------------- |     config/         |
|                              +---------------------+
|                                      |
|                    SwaggerConfig/, SecurityConfig/, RedisConfig/
|
|                               +---------------------+
|-----------------------------  |    resources/       |
|                               +---------------------+
|                                       |
|                       +---------------+---------------+
|                       |                               |
|                 templates/                        static/
|                       |
|            reports/portfolio-report.html

```
# Full Folder Structure
```
portoquant/
└── src/
    └── main/
        ├── java/
        │   └── com/quant/portoquant/
        │       ├── PortoquantApplication.java
        │       ├── api/
        │       │   ├── controller/
        │       │   │   ├── AssetController.java
        │       │   │   ├── AuthController.java
        │       │   │   ├── HealthController.java
        │       │   │   ├── PortfolioController.java
        │       │   │   ├── ReportController.java
        │       │   │   ├── SimulationController.java
        │       │   │   └── UserController.java
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
        │       │   │   ├── HistoricalDataLoaderService.java
        │       │   │   ├── PortfolioService.java
        │       │   │   ├── RedisCacheService.java
        │       │   │   ├── ReportService.java
        │       │   │   ├── SimulationService.java
        │       │   │   ├── TickerCacheManager.java
        │       │   │   └── UserService.java
        │       │   ├── service/impl/
        │       │   │   ├── AssetServiceImpl.java
        │       │   │   ├── HistoricalDataLoaderServiceImpl.java
        │       │   │   ├── PortfolioServiceImpl.java
        │       │   │   ├── RedisCacheServiceImpl.java
        │       │   │   ├── ReportServiceImpl.java
        │       │   │   ├── SimulationServiceImpl.java
        │       │   │   ├── TickerCacheManagerImpl.java
        │       │   │   └── UserServiceImpl.java
        │       │   └── service/assembler/
        │       │       └── PortfolioReportAssembler.java
        │       ├── config/
        │       │   ├── ModelMapperConfig.java
        │       │   ├── RedisConfig.java
        │       │   ├── SecurityConfig.java
        │       │   └── SwaggerConfig.java
        │       ├── domain/
        │       │   ├── analytical/
        │       │   │   ├── ConstantExpectedReturnModel.java
        │       │   │   ├── ConstantVolatilityModel.java
        │       │   │   ├── ExpectedReturnModel.java
        │       │   │   ├── TimeVaryingExpectedReturnModel.java
        │       │   │   ├── TimeVaryingVolatility.java
        │       │   │   └── VolatilityModel.java
        │       │   ├── calculations/
        │       │   │   ├── PortfolioAnalyticsUtil.java
        │       │   │   ├── RiskMetricsCalculator.java
        │       │   ├── calculations/registry/
        │       │   │   └── ModelCalculatorRegistry.java
        │       │   ├── calculations/returnestimations/
        │       │   │   ├── BondExpectedReturnCalculator.java
        │       │   │   ├── CashExpectedReturnCalculator.java
        │       │   │   ├── CommoditiyReturnCalculator.java
        │       │   │   ├── CryptoExpectedReturnCalculator.java
        │       │   │   ├── ExpectedReturnCalculator.java
        │       │   │   ├── MutualFundReturnCalculator.java
        │       │   │   ├── RealEstateExpectedReturnCalculator.java
        │       │   │   └── StockExpectedReturnCalculator.java
        │       │   ├── calculations/volatilityestimations/
        │       │   │   ├── BondVolatilityCalculator.java
        │       │   │   ├── CashVolatilityCalculator.java
        │       │   │   ├── CommodityVolatilityCalculator.java
        │       │   │   ├── CryptoVolatilityCalculator.java
        │       │   │   ├── MutualFundVolatilityCalculator.java
        │       │   │   ├── RealEstateVolatilityCalculator.java
        │       │   │   ├── StockVolatilityCalculator.java
        │       │   │   └── VolatilityCalculator.java
        │       │   ├── garchmodel/
        │       │   │   └── GarchModel.java
        │       │   ├── model/
        │       │   │   ├── Asset.java
        │       │   │   ├── Portfolio.java
        │       │   │   ├── SimulationResult.java
        │       │   │   └── User.java
        │       │   ├── model/assets/
        │       │   │   ├── AssetFactory.java
        │       │   │   ├── Bond.java
        │       │   │   ├── Cash.java
        │       │   │   ├── Commodity.java
        │       │   │   ├── Crypto.java
        │       │   │   ├── MutualFund.java
        │       │   │   ├── RealEstate.java
        │       │   │   └── Stock.java
        │       │   ├── model/enums/
        │       │   │   ├── AssetType.java
        │       │   │   ├── SimulationStatus.java
        │       │   │   └── UserRole.java
        │       │   ├── simulation/context/
        │       │   │   ├── GBMSimulatorContext.java
        │       │   │   └── SimulationContext.java
        │       │   ├── simulation/engine/
        │       │   │   └── MultiMonteCarloSimulation.java
        │       │   ├── simulation/executors/
        │       │   │   └── MonteCarloSimulationExecutor.java
        │       │   ├── simulation/model/
        │       │   │   └── GBMSimulator.java
        │       │   └── simulation/runner/
        │       │       └── SimulationRunner.java
        │       ├── infrastructure/
        │       │   ├── cache/rediscache/
        │       │   │   ├── RedisEvictionPolicyManager.java
        │       │   │   └── RedisTickerCacheLoader.java
        │       │   ├── exception/
        │       │   │   ├── ApiError.java
        │       │   │   ├── BadRequestException.java
        │       │   │   ├── GlobalExceptionHandler.java
        │       │   │   ├── ResourceNotFoundException.java
        │       │   │   └── VintageApiException.java
        │       │   ├── historicaldata/
        │       │   │   ├── datasources/apiclients/
        │       │   │   │   └── VintageStockClient.java
        │       │   │   ├── datasources/csv/
        │       │   │   ├── datasources/parsers/
        │       │   │   ├── models/
        │       │   │   │   ├── HistoricalDataMeta.java
        │       │   │   │   └── HistoricalPrice.java
        │       │   │   ├── provider/
        │       │   │   │   ├── HistoricalDataProvider.java
        │       │   │   │   ├── HistoricalDataSourceRegistry.java
        │       │   │   │   └── StockApiDataProvider.java
        │       │   │   └── rolling/
        │       │   │       └── RollingWindowManager.java
        │       │   ├── models/
        │       │   ├── reports/context/
        │       │   │   ├── PortfolioReportContextBuilder.java
        │       │   │   └── ReportContextBuilder.java
        │       │   ├── reports/executor/
        │       │   │   ├── PortfolioReportExecutor.java
        │       │   │   └── ReportExecutor.java
        │       │   ├── reports/generator/
        │       │   │   ├── ChartRenderer.java
        │       │   │   └── PdfReportGenerator.java
        │       │   ├── reports/models/
        │       │   │   ├── AssetSummary.java
        │       │   │   ├── PortfolioReportData.java
        │       │   │   └── SimulationSummary.java
        │       │   ├── reports/models/charts/
        │       │   │   ├── AssetDistributionChart.java
        │       │   │   └── PieChart.java
        │       │   ├── repository/
        │       │   │   ├── AssetRepository.java
        │       │   │   ├── HistoricalDataMetaRepository.java
        │       │   │   ├── HistoricalPriceRepository.java
        │       │   │   ├── PortfolioRepository.java
        │       │   │   ├── SimulationResultRepository.java
        │       │   │   └── UserRepository.java
        │       │   ├── scheduler/
        │       │   │   ├── HistoricalDataSyncScheduler.java
        │       │   │   └── RedisFrequencySyncScheduler.java
        │       │   └── security/
        │       │       ├── auth/
        │       │       │   ├── AuthService.java
        │       │       │   └── AuthServiceImpl.java
        │       │       ├── jwt/
        │       │       │   ├── JwtAuthenticationFilter.java
        │       │       │   ├── JwtService.java
        │       │       │   └── JwtServiceImpl.java
        │       │       └── user/
        │       │           ├── AuthUserDetails.java
        │       │           └── AuthUserDetailService.java
        └── resources/
            ├── application.yml
            ├── application.properties
            ├── static/
            └── templates/
                └── reports/
                    └── portfolio-report.html

```
