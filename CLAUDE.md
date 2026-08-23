# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Development Commands

### Building and Running
- Build the project: `./mvnw clean package`
- Run the application: `./mvnw spring-boot:run`
- Run tests: `./mvnw test`
- Run a single test: `./mvnw test -Dtest=<TestClassName>`

### Docker
- Build Docker image: `docker build -t leetcode-progression-tracker .`
- Run with Docker compose: `docker compose up`

### API Endpoints
- Problems: `GET/POST /api/problems` and `GET /api/problems/{id}`
- Attempts: `GET/POST /api/problems/{problemId}/attempts` and `GET /api/problems/attempts/{attemptId}`

## Project Structure

### Architecture Overview
This is a Spring Boot application tracking LeetCode problem progression with:
- REST controllers handling API requests
- Service layer containing business logic
- Repository layer using Spring Data JPA
- Entity models for database persistence
- DTOs for API request/response objects
- In-memory H2 database for storage

### Key Components

#### Controllers (`src/main/java/com/kylewelch/leetcode_progression_tracker/controller/`)
- `ProblemController`: CRUD operations for LeetCode problems
- `AttemptController`: Manage attempts on problems
- `AiController`: AI integration endpoints
- `ApiController`: Health/status endpoints

#### Services (`src/main/java/com/kylewelch/leetcode_progression_tracker/service/`)
- `ProblemService`: Business logic for problem management
- `AttemptService`: Business logic for attempt tracking
- `AiService`: Integration with AI models

#### Models (`src/main/java/com/kylewelch/leetcode_progression_tracker/model/`)
- `Problem`: Represents a LeetCode problem with title, difficulty, URL, notes
- `Attempt`: Tracks attempts on problems with success status and notes

#### Repositories (`src/main/java/com/kylewelch/leetcode_progression_tracker/repository/`)
- `ProblemRepository`: Spring Data JPA repository for Problem entities
- `AttemptRepository`: Spring Data JPA repository for Attempt entities

#### Configuration
- `LeetcodeProgressionTrackerApplication`: Main Spring Boot application class
- Configures ChatMemory for AI conversations (25 message window)

### Data Flow
1. HTTP requests hit Controllers
2. Controllers delegate to Services
3. Services interact with Repositories
4. Repositories persist/retrieve Entities from H2 database
5. Services convert Entities to/from DTOs for API boundary

### External Integrations
- Spring AI with OpenAI models for AI-powered features
- H2 in-memory database (configured via spring-boot-starter-data-jpa)
- Validation via spring-boot-starter-validation

## Common Development Tasks

### Adding New Features
1. Create/update DTOs in `dto/` package
2. Update Entity models if needed in `model/` package
3. Modify Repository interfaces if new queries needed
4. Implement business logic in Service classes
5. Expose functionality via Controller endpoints
6. Add tests in corresponding test classes

### Database Changes
- Entities use JPA annotations for table/column mapping
- Changes to models automatically update schema via Hibernate
- Initial data can be added via `data.sql` or `schema.sql` in resources

### AI Features
- AI service uses Spring AI's ChatMemory for context retention
- MessageWindowChatMemory limits conversations to 25 messages
- AI endpoints typically in AiController