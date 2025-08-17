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
