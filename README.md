Project Overview
Project Title: Client Feedback Analysis System

Purpose: To collect, categorize, and analyze client feedback using NLP and sentiment analysis, enabling organizations to identify recurring issues and understand client emotions.

Target Users:

Clients: Submit feedback via REST API

Admin/Support Teams: View categorized and sentiment-tagged feedback, search/filter entries, and generate frequency reports

Managers: Use reports to guide decision-making and improve client satisfaction

Sprint Goal (MVP - Oriented)
Deliver a working Spring Boot application that:

Feedback submission API

Categorizes feedback using keyword matching

Implement Sentiment Analysis

Admin API for filtering feedback by category, sentiment, and date

Generates weekly frequency and sentiment reports

Includes unit tests for all core modules

Sprint Duration
Start Date: 11th August 2025

End Date: 21st August 2025

Duration: 11 Days

User Stories
ID

User Story

Priority

Est. Time

Story Points

US1

As a client, I want to submit feedback via an API so that my experience is recorded.

High

1 day

2

US2

As a developer, I want to set up a Spring Boot project with required dependencies so that development can begin.

High

1 day

2

US3

As a developer, I want submitted feedback to be stored securely in a database with timestamps so that it can be retrieved and analyzed later.

High

1 day

2

US4

As an admin, I want feedback to be categorized and sentiment-tagged (using keyword matching and/or NLP) so I can understand and group client concerns.

High

1.5 days

3

US5

As an admin, I want to filter feedback by category, sentiment, and date so that I can quickly find relevant data.

High

1 day

2

US6

As a manager, I want weekly reports showing recurring issues and sentiment trends so that I can track overall performance.

Medium

1 day

2

US7

As a system, I want to generate frequency reports of recurring issues so that admins can identify top concerns.

Medium

1 day

2

US8

As an admin, I want to export feedback reports in CSV or PDF so that I can share them with stakeholders.

Medium

1.5 days

3

US9

As a user, I want role-based access control (Admin vs General User) so that only authorized users can view sensitive reports.

High

1 day

2

US10

As an admin, I want a dashboard summarizing categorized trends and recurring issues so I can get an overview.

Medium

1.5 days

3

US11

As a developer, I want unit and integration tests for all APIs so the system is robust and reliable.

High

1 day

2

US12

As a user/admin, I want comprehensive API documentation so it is easy to understand and use the system.

High

1 day

2

US13

As a developer, I want to deploy the solution to a cloud or local server for production use.

Medium

1 day

2

System Architecture
Architecture Overview: 

Client → Spring Boot REST API → NLP Service → PostgreSQL → Admin API → Reporting Engine

Layers: 

Controller Layer: Handles HTTP requests

Service Layer: Business logic for feedback processing

Repository Layer: JPA-based DB operations

Model Layer: Entity classes for Feedback and Client

Utility Layer: NLP and sentiment analysis helpers

image.jpg
Tech Stack:

Language: Java / Spring Boot

Database: PostgreSQL / H2 (for mock/test)

ORM: Spring Data JPA

Libraries: Java NLP Libraries (e.g., Stanford NLP, Open NLP) for Sentiment Analysis

Auth: Role-based via Spring Security

API Communication: REST Template for external mock APIs

Documentation: Swagger / Open API

Build Tool: Maven

Testing: JUnit + Mockito

Development Tools: IntelliJ IDEA, Postman, Git & GitHub

Mock APIs to Simulate External Checks
API Name

Description

Endpoint

Sentiment Analysis

Simulates sentiment tagging of feedback text

POST /mock/sentiment

Keyword Categorization

Simulates keyword-based categorization of feedback

POST /mock/categorize

Feedback Summary

Returns mock frequency data for recurring issues

GET /mock/summary

Report Export

Simulates exporting filtered feedback reports in PDF or CSV format

POST /mock/export

Role-Based Access Check

Simulates checking if a user has permission to access a resource

POST /mock/access-check

Core Modules
Feedback Submission Module 

Handles incoming feedback from clients via a REST API. 

It validates input, assigns a unique ID, and stores the feedback along with a timestamp in the database.

Keyword Categorization Module

Analyzes the feedback text using keyword matching to assign relevant categories (e.g., billing, support, delay).

Sentiment Analysis Module

Processes feedback to determine its emotional tone—positive, neutral, or negative. 

It uses NLP APIs to tag sentiment, aiding in understanding client mood.

Admin Search & Filter Module

Provides APIs for admins to search and filter feedback based on category, sentiment, and date. 

Enables quick access to relevant feedback for investigation or reporting.

Reporting Module

Generates weekly frequency reports showing how often each category appears, along with sentiment trends. 

Helps managers identify recurring issues and monitor client satisfaction.

Unit Testing Module

Includes JUnit and Mockito-based tests for all core modules. 

Ensures reliability, correctness, and maintainability of the system through automated test coverage.

Core Requirements
Entity Classes: 
1. Feedback
Long id

String clientId

String text

LocalDateTime submittedAt

List<Category> categories

2. Category
Long id

String name

3. FeedbackCategory
Long id

Feedback feedback

Category category

4. Admin
Long id

String username

String passwordHash

String role

5. FrequencyReport
String categoryName

int count

LocalDate startDate

LocalDate endDate

Sample Endpoints
Endpoint

Method

Role

Description

/api/feedback

POST

Client

Submits feedback with message and client ID

/api/feedback

GET

Admin

Retrieves feedback with optional filters (category, sentiment, date)

/api/report/weekly

GET

Manager/Admin

Generates weekly frequency and sentiment report

/api/feedback/{id}/resolve

PATCH

Admin

Marks a specific feedback entry as resolved

/api/export

POST

Admin

Exports filtered feedback data in CSV or PDF format

/api/access-check

POST

System

Verifies if a user has permission to access a specific resource

/api/dashboard/summary

GET

Admin

Returns a summary of categorized feedback and sentiment trends

/api/docs

GET

User/Admin

Provides Swagger-generated API documentation

Acceptance Criteria
Feedback is stored and categorized correctly

Sentiment analysis is accurate and consistent

Admin can filter feedback by category, sentiment, and date

Weekly reports show correct frequency and sentiment distribution

All core modules have unit tests with >80% coverage

API documentation is available via Swagger

Deliverables
Mock API Services

PostgreSQL DB schema

Unit tests for all services

REST API + Swagger docs

Postman collection 

API Documentation

Project Report

Learning Objectives
Build a full-stack RESTful application using Spring Boot and Java

Apply object-oriented programming principles in system design

Integrate basic NLP for keyword categorization and sentiment analysis

Design and document REST APIs using Swagger/OpenAPI

Implement secure data storage with Spring Data JPA and PostgreSQL

Write unit and integration tests using JUnit and Mockito

Implement role-based access control for secure data access

Repository Structure


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





Add a comment

