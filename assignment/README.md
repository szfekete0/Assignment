# Assignment API
Just some explanation of this work before moving forward. 
My vision was to convert the assignment into a more “production-like” solution by adopting more real-world compatible 
libraries and technologies. That’s why I decided to use Liquibase to migrate the CSVs into MySQL and add Docker support 
into the picture. During the development I used codex to be able to ship this solution in time.


## Tech Stack

- Java 21
- Spring Boot 4
- Spring Data JPA
- Liquibase
- MySQL 8.4
- MapStruct

## Startup

## Option 1: Docker Compose (recommended)

From the `assignment` folder:

```bash
docker compose up --build
```

Services:

- App: `http://localhost:8080`
- MySQL: `localhost:3306`

Compose already provides datasource env vars for the app.

## Option 2: Run app locally (DB in Docker)

1. Start only MySQL:

```bash
docker compose up mysql -d
```

2. Run the `assignment` service from IntellJ:

Note: Compile first to have the mapstruct implementation generated

Even on in this case the connection creds should be taken care by spring-boot-docker-compose but
If needed your own props, set datasource env vars:

- `SPRING_DATASOURCE_URL=`
- `SPRING_DATASOURCE_USERNAME=`
- `SPRING_DATASOURCE_PASSWORD=`

## Run Tests

From the `assignment` folder:

```bash
./mvnw test
```

Windows PowerShell:

```powershell
.\mvnw.cmd test
```

## API Endpoints

Base URL: `http://localhost:8080`

## Member Participation

- `GET /api/member/{memberId}/points`
  - Returns aggregated member points + per-survey points
- `GET /api/member/{memberId}/surveys/completed`
  - Returns surveys completed by member

## Survey Participation

- `GET /api/survey/{surveyId}/submitters`
  - Returns members who completed the survey
- `GET /api/survey/{surveyId}/invitees`
  - Returns active members who have not participated in the survey
- `GET /api/survey/stats`
  - Returns per-survey statistics
