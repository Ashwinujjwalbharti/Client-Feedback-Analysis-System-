# CLient Feedback Analysis System
## Project Overview
**Project Title:** Client Feedback Analysis System <br>

**Purpose:** To collect, categorize, and analyze client feedback using NLP and sentiment analysis, enabling organizations to identify recurring issues and understand client emotions. <br> 

**Target Users:** <br>
- **Clients:** Submit feedback via REST API. <br>
- **Admin/Support Teams:** View categorized and sentiment-tagged feedback, search/filter entries, and generate frequency reports. <br>
- **Managers:** Use reports to guide decision-making and improve client satisfaction. <br>

## Sprint Goal (MVP - Oriented) <br>
Deliver a working Spring Boot application that:
- Feedback submission API
- Categorizes feedback using keyword matching
- Implement Sentiment Analysis
- Admin API for filtering feedback by category, sentiment, and date
- Generates weekly frequency and sentiment reports
- Includes unit tests for all core modules

## Sprint Duration <br>
**Start Date:** 11th August 2025 <br>
**End Date:** 21th August 2025 <br>
**Duration:** 25 days <br>

## User Stories <br> 
| ID   | User Story                                                                                          | Priority | Est. Time | Story Points  |
|------|-----------------------------------------------------------------------------------------------------|----------|-----------|---------------|
| US1  | As a client, I want to submit feedback via an API so that my experience is recorded.                | High     | 1 day     | 2             |
| US2  | As a developer, I want to set up a Spring Boot project with required dependencies.                  | High     | 1 day     | 2             |
| US3  | As a developer, I want submitted feedback to be stored securely in a database with timestamps.      | High     | 1 day     | 2             |
| US4  | As an admin, I want feedback to be categorized and sentiment-tagged using keyword matching/NLP.     | High     | 1.5 days  | 3             |
| US5  | As an admin, I want to filter feedback by category, sentiment, and date.                            | High     | 1 day     | 2             |
| US6  | As a manager, I want weekly reports showing recurring issues and sentiment trends.                  | Medium   | 1 day     | 2             |
| US7  | As a system, I want to generate frequency reports of recurring issues.                              | Medium   | 1 day     | 2             |
| US8  | As an admin, I want to export feedback reports in CSV or PDF.                                       | Medium   | 1.5 days  | 3             |
| US9  | As a user, I want role-based access control (Admin vs General User).                                | High     | 1 day     | 2             |
| US10 | As an admin, I want a dashboard summarizing categorized trends and recurring issues.                | Medium   | 1.5 days  | 3             |
| US11 | As a developer, I want unit and integration tests for all APIs.                                     | High     | 1 day     | 2             |
| US12 | As a user/admin, I want comprehensive API documentation.                                            | High     | 1 day     | 2             |
| US13 | As a developer, I want to deploy the solution to a cloud or local server for production use.        | Medium   | 1 day     | 2             |
<br>

## System Architecture <br>
**Architecture Overview:** <br>
Client -> Spring Boot REST API -> NLP Service -> PostgreSQL -> Admin API -> Reporting Engine <br>

**Layers:** <br>
- **Controller Layer:** Handles HTTP requests <br>
- **Service Layer:** Business logic for feedback processing <br>
- **Repository Layer:** JPA-based DB operations <br>
- **Model Layer:** Entity classes for Feedback and Client <br>
- **Utility Layer:** NLP and sentiment analysis helpers <br>

**Tech Stack:** <br>
- **Language:** Java / Spring Boot
- **Database:** PostgreSQL / H2 (for mock/test)
- **ORM:** Spring Data JPA
- **Libraries:** Java NLP Libraries (e.g., Stanford NLP, Open NLP) for Sentiment Analysis
- **Auth:** Role-based via Spring Security
- **API Communication:** REST Template for external mock APIs
- **Documentation:** Swagger / Open API
- **Build Tool:** Maven
- **Testing:** JUnit + Mockito
- **Development Tools:** IntelliJ IDEA, Postman, Git & GitHub

## Mock APIs to Simulate External Checks <br>

| API Name               | Description                                                      | Endpoint                |
|------------------------|------------------------------------------------------------------|-------------------------|
| Sentiment Analysis     | Simulates sentiment tagging of feedback text                     | POST /mock/sentiment    |
| Keyword Categorization | Simulates keyword-based categorization of feedback               | POST /mock/categorize   |
| Feedback Summary       | Returns mock frequency data for recurring issues                 | GET /mock/summary       |
| Report Export          | Simulates exporting filtered feedback reports in PDF or CSV      | POST /mock/export       | 
| Role-Based Access Check| Simulates checking if a user has permission to access a resource | POST /mock/access-check |

## Core Modules <br>
**1. Feedback Submission Module** <br>
- Handles incoming feedback from clients via a REST API. <br>
- It validates input, assigns a unique ID, and stores the feedback along with a timestamp in the database. <br>

**2. Keyword Categorization Module** <br>
- Analyzes the feedback text using keyword matching to assign relevant categories (e.g., billing, support, delay). <br>

**3. Sentiment Analysis Module** <br>
- Processes feedback to determine its emotional tone-positive, neutral, or negative. <br>
- It uses NLP APIs to tag sentiment, aiding in understanding client mood. <br>

**4. Admin Search & Filter Module** <br>
- Provides APIs for admins to search and filter feedback based on category, sentiment, and date. <br>
- Enables quick access to relevant feedback for investigation or reporting. <br>

**5. Reporting Module** <br>
- Generates weekly frequency reports showing how often each category appears, along with sentiment trends. <br>
- Helps managers identify recurring issues and monitor client satisfaction. <br>

**6. Unit Testing Module** <br>
- Includes JUnit and Mockito-based tests for all core modules. <br>
- Ensures reliability, correctness, and maintainability of the system through automated test coverage. <br>

## Sample Endpoints <br>
| Endpoint                  | Method | Role           | Description                                                  |
|---------------------------|--------|----------------|--------------------------------------------------------------|
| /api/feedback             | POST   | Client         | Submits feedback with message and client ID                  |
| /api/feedback             | GET    | Admin          | Retrieves feedback with optional filters                     |
| /api/report/weekly        | GET    | Manager/Admin  | Generates weekly frequency and sentiment report              |
| /api/feedback/{id}/resolve| PATCH  | Admin          | Marks a specific feedback entry as resolved                  |
| /api/export               | POST   | Admin          | Exports filtered feedback data in CSV or PDF format          |
| /api/access-check         | POST   | System         | Verifies if a user has permission to access a specific resource |
| /api/dashboard/summary    | GET    | Admin          | Returns a summary of categorized feedback and sentiment trends |
| /api/docs                 | GET    | User/Admin     | Provides Swagger-generated API documentation                  |


## Acceptance Criteria <br>
- Feedback is stored and categorized correctly  
- Sentiment analysis is accurate and consistent  
- Admin can filter feedback by category, sentiment, and date  
- Weekly reports show correct frequency and sentiment distribution  
- All core modules have unit tests with >80% coverage  
- API documentation is available via Swagger  

## Deliverables <br>
- Mock API Services  
- PostgreSQL DB schema  
- Unit tests for all services  
- REST API + Swagger docs  
- Postman collection  
- API Documentation  
- Project Report  

## Learning Objectives <br>
- Build a full-stack RESTful application using Spring Boot and Java  
- Apply object-oriented programming principles in system design  
- Integrate basic NLP for keyword categorization and sentiment analysis  
- Design and document REST APIs using Swagger/OpenAPI  
- Implement secure data storage with Spring Data JPA and PostgreSQL  
- Write unit and integration tests using JUnit and Mockito  
- Implement role-based access control for secure data access  

## Repository Structure <br>
client-feedback-analysis/
│
├── src/
│   ├── main/
│   │   ├── java/com/example/feedback/
│   │   │   ├── controller/        # REST controllers for handling API requests
│   │   │   ├── service/           # Business logic and NLP processing
│   │   │   ├── model/             # Entity classes like Feedback and Client
│   │   │   ├── repository/        # Spring Data JPA interfaces
│   │   │   └── util/              # Utility classes for categorization and sentiment
│   │   └── resources/
│   │       └── application.yml    # Configuration file
│
├── test/
│   └── java/com/example/feedback/
│       ├── service/               # Unit tests for service layer
│       └── controller/            # Unit tests for controller layer
│
├── docs/
│   ├── API.md                     # API documentation
│   ├── Report.pdf                 # Final project report
│   └── SystemDesign.png           # System architecture diagram
│
├── pom.xml / build.gradle         # Build configuration
└── README.md                      # Project overview and setup instructions
