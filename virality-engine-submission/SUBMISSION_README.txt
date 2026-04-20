================================================================================
BACKEND ENGINEERING ASSIGNMENT - VIRALITY ENGINE
================================================================================

Submitted by: Atul Jha
GitHub: https://github.com/atuljha-tech/backend_engineering_springboot

================================================================================
TECH STACK
================================================================================
- Java 17
- Spring Boot 3.2.0
- PostgreSQL 15
- Redis 7
- Maven
- Docker & Docker Compose

================================================================================
QUICK START
================================================================================

1. Start Infrastructure:
   docker-compose up -d

2. Build and Run:
   mvn clean install
   mvn spring-boot:run

3. Create Test Data:
   ./setup-test-data.sh

4. Test API:
   Import postman_collection.json into Postman

5. Test Race Conditions:
   ./test-race-condition.sh

================================================================================
PROJECT STRUCTURE
================================================================================

virality-engine/
├── src/main/java/com/assignment/viralityengine/
│   ├── ViralityEngineApplication.java
│   ├── config/          (Redis configuration)
│   ├── controller/      (REST endpoints)
│   ├── dto/             (Request/Response objects)
│   ├── entity/          (JPA entities: User, Bot, Post, Comment)
│   ├── repository/      (JPA repositories)
│   └── service/         (Business logic)
├── src/main/resources/
│   └── application.properties
├── docker-compose.yml
├── pom.xml
├── postman_collection.json
├── setup-test-data.sh
└── test-race-condition.sh

================================================================================
KEY FEATURES IMPLEMENTED
================================================================================

✓ Phase 1: Core API & Database Setup
  - JPA entities with proper relationships
  - REST endpoints: POST /api/posts, /api/posts/{id}/comments, /api/posts/{id}/like

✓ Phase 2: Redis Virality Engine & Atomic Locks
  - Virality scoring: Bot Reply +1, Human Like +20, Human Comment +50
  - Horizontal Cap: Max 100 bot replies per post
  - Vertical Cap: Max 20 comment depth levels
  - Cooldown Cap: 10-minute bot-human interaction cooldown

✓ Phase 3: Notification Engine
  - 15-minute notification throttling
  - Smart batching with Redis Lists
  - CRON job every 5 minutes for summarization

✓ Phase 4: Corner Cases & Testing
  - Race condition handling (200 concurrent requests)
  - Stateless architecture (all state in Redis)
  - Thread-safe atomic operations

================================================================================
THREAD SAFETY GUARANTEE
================================================================================

The application uses Redis atomic operations to ensure thread safety:

1. All counters use Redis INCR (atomic operation)
2. Cooldowns use Redis SETEX with TTL
3. Checks happen before increments
4. No Java memory state (HashMap, static variables)
5. Redis single-threaded execution ensures sequential processing

When 200 concurrent bot comment requests hit the API:
- All requests check if bot_count < 100
- Redis INCR operations execute sequentially
- Exactly 100 requests succeed (201 Created)
- Exactly 100 requests fail (429 Too Many Requests)
- Database contains exactly 100 bot comments

================================================================================
API ENDPOINTS
================================================================================

POST /api/posts
{
  "authorId": 1,
  "authorType": "USER",
  "content": "Hello World"
}

POST /api/posts/{postId}/comments
{
  "authorId": 1,
  "authorType": "BOT",
  "content": "Great post!",
  "parentCommentId": null
}

POST /api/posts/{postId}/like
{
  "userId": 1
}

================================================================================
TESTING
================================================================================

Race Condition Test:
./test-race-condition.sh

Expected Result:
- Exactly 100 bot comments created
- 100 requests return 201 Created
- 100 requests return 429 Too Many Requests

Verify Redis:
docker exec -it assignment-redis redis-cli
GET post:1:bot_count
GET post:1:virality_score

Verify Database:
docker exec -it assignment-postgres psql -U admin -d virality_db
SELECT COUNT(*) FROM comments WHERE post_id = 1 AND author_type = 'BOT';

================================================================================
NOTES
================================================================================

- All code files contain ZERO comments (as requested)
- Application is completely stateless
- Horizontally scalable architecture
- Production-ready error handling and validation
- Comprehensive documentation included

================================================================================
DOCUMENTATION FILES
================================================================================

- README.md: Complete setup and thread safety explanation
- QUICKSTART.md: Quick start guide
- IMPLEMENTATION_SUMMARY.md: Detailed implementation overview
- PROJECT_STRUCTURE.md: Project structure documentation
- postman_collection.json: API testing collection

================================================================================
CONTACT
================================================================================

GitHub: https://github.com/atuljha-tech/backend_engineering_springboot

================================================================================
