# XMeme

XMeme is a **REST API for creating and sharing memes**. Users can post memes (name, caption, image URL), retrieve the latest 100 memes, and fetch individual memes by ID.

## API Endpoints

| Method | Endpoint | Description | Response |
|--------|----------|-------------|----------|
| POST | `/memes/` | Create a new meme | 201 Created + `{ id }` |
| GET | `/memes/` | Get latest 100 memes | 200 OK + `List<MemeResponse>` |
| GET | `/memes/{id}` | Get a single meme by ID | 200 OK + `MemeResponse` / 404 Not Found |

## Execution Flow

```
POST /memes/ (with MemeRequest: name, caption, url)
|
+-- MemeController
|   +-- Receives MemeRequest via @RequestBody
|   +-- Delegates to MemeService.createMeme(request)
|
+-- MemeService
|   +-- Validates: checks for null/empty fields
|   +-- Checks for duplicate memes
|   +-- Creates MemeEntity with createdAt timestamp
|   +-- Calls MemeRepository.save(entity)
|   +-- Returns MemeIdResponse with generated ID
|
+-- MemeRepository (MongoRepository<MemeEntity, String>)
|   +-- Persists to MongoDB "memes" collection
|
+-- Response: HTTP 201 + MemeIdResponse { id }


GET /memes/
|
+-- MemeController
|   +-- Delegates to MemeService.getLatestMemes()
|
+-- MemeService
|   +-- Calls MemeRepository.findTop100ByOrderByCreatedAtDesc()
|   +-- Maps MemeEntity -> MemeResponse
|
+-- Response: HTTP 200 + List<MemeResponse>
```

## Key Entities

| Layer | Class | Role |
|-------|-------|------|
| Controller | `MemeController` | REST endpoints for meme CRUD |
| Service | `MemeService` | Validation, duplicate check, entity mapping |
| Repository | `MemeRepository` | MongoDB data access via MongoRepository |
| Entity | `MemeEntity` | MongoDB document — id, name, caption, url, createdAt |
| DTO | `MemeRequest` | Input — name, caption, url |
| DTO | `MemeResponse` | Output — id, name, caption, url |
| DTO | `MemeIdResponse` | Output — id (returned on creation) |

## Design Patterns Used

- **Layered Architecture** — Controller -> Service -> Repository -> Entity
- **Repository Pattern** — MongoRepository for data access abstraction
- **DTO Pattern** — Separate request/response objects decoupled from entity
- **Dependency Injection** — Constructor-based DI via Spring

## Technologies & Modules Used

### Core Framework
- **Spring Boot 2.7.1** — auto-configuration, dependency injection, application startup
- **Spring MVC (REST)** — `@RestController`, `@PostMapping`, `@GetMapping`
- **Spring Data MongoDB** — `MongoRepository`, entity mapping with `@Document`

### Database
- **MongoDB** — NoSQL document database (database name: `Xmeme`, port 27017)

### Libraries
- **Lombok 1.18.24** — `@Data`, `@AllArgsConstructor`, `@NoArgsConstructor`
- **Jackson** — JSON serialization/deserialization (bundled with Spring Boot)

### Testing
- **JUnit 5** — unit testing framework
- **Mockito** — mocking dependencies (`@MockBean`, `@Mock`, `@InjectMocks`)
- **Spring MockMvc** — testing REST endpoints without starting the server

### Code Quality
- **Checkstyle** — Google Java Style Guide enforcement
- **SpotBugs 4.7.1** — bug pattern detection
- **JaCoCo 0.8.8** — code coverage reporting (XML)
- **PMD** — static code analysis

### Build & Deployment
- **Gradle** — build automation
- **Docker** — containerization (Dockerfile included, base image: `gradle:jdk11-focal`)
- **Java 11**

The app runs on **port 8081**.
