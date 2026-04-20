This backend assignment implements a high-throughput Virality Engine using Java 17, Spring Boot 3.2, and PostgreSQL, designed to handle 200+ concurrent requests securely. It ensures data consistency under heavy load via thread-safe atomic operations using Redis (distributed locking/incrementing), containerized via Docker Compose. 
GitHub
GitHub
 +4
Virality Engine
A robust backend engine designed to manage viral interactions (like clicks, shares, or referrals) atomically, ensuring accurate counts even under high concurrency.
Tech Stack
Java 17
Spring Boot 3.2
PostgreSQL (Persistent Data)
Redis (Atomic Counters / Lock Management)
Docker & Docker Compose (Containerization) 
Core Features
Thread-Safe Operations: Handles concurrent requests (200+) using Redis atomic operations to prevent race conditions during viral count updates.
Infrastructure as Code: Fully containerized setup via docker-compose.yml.
Data Integrity: PostgreSQL ensures persistent storage, while Redis handles volatile, high-speed transactions. 
GitHub
GitHub
 +2
Getting Started
Prerequisites
Docker & Docker Compose installed.
Java 17 (if running outside Docker). 
Running the Application
Clone the repository:
bash
git clone <your-repo-url>
cd virality-engine
Start Services (Postgres & Redis):
bash
docker-compose up -d
Run the application:
bash
./mvnw spring-boot:run
 
API Testing
A Postman collection ViralityEngine.postman_collection.json is provided in the docs/ folder.
POST /api/v1/virality/trigger - Trigger a viral event.
Validation Strategy (Race Conditions)
To validate the 200 concurrent requests requirement, a test script is provided in scripts/load_test.sh. It uses Apache Bench (ab) or similar tools to simulate high traffic and compares the final Redis count against the DB final state. 
LinkedIn
LinkedIn
bash
# Example command in scripts/
./load_test.sh
Submission Checklist
GitHub repository with full project
Thread-safe atomic operations using Redis
Docker Compose for infrastructure
Postman collection for API testing
Test scripts for race condition validation 
