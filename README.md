# 🚀 Virality Engine — High-Throughput Backend System

A production-ready backend system built to handle **high-concurrency viral interactions** (clicks, shares, referrals) with strong guarantees around **data consistency**, **atomicity**, and **scalability**.

Designed using **Java 17**, **Spring Boot 3.2**, **PostgreSQL**, and **Redis**, this engine ensures accurate counting and robust performance even under **200+ concurrent requests**.

---

## 📌 Overview

The **Virality Engine** simulates real-world viral systems where multiple users trigger events simultaneously. The system is engineered to:

* Prevent race conditions
* Maintain accurate counts
* Scale under heavy traffic
* Ensure persistent and consistent data storage

---

## 🧱 Tech Stack

| Layer            | Technology                    | Purpose                              |
| ---------------- | ----------------------------- | ------------------------------------ |
| Backend          | Java 17, Spring Boot 3.2      | Core application logic               |
| Database         | PostgreSQL                    | Persistent, reliable data storage    |
| Cache / Sync     | Redis                         | Atomic counters, distributed locking |
| Containerization | Docker & Docker Compose       | Infrastructure and environment setup |
| Testing          | Apache Bench / Custom Scripts | Load and concurrency testing         |

---

## ⚙️ Core Features

### 🔒 Thread-Safe Operations

* Uses **Redis atomic operations** (`INCR`, locks) to handle concurrent updates.
* Eliminates race conditions during high-frequency event triggers.

### ⚡ High Throughput Handling

* Supports **200+ concurrent requests** without data inconsistency.
* Optimized for low latency and fast response times.

### 🧠 Data Integrity

* **Redis** handles high-speed, in-memory operations.
* **PostgreSQL** ensures durable and consistent persistence.

### 🐳 Infrastructure as Code

* Fully containerized using `docker-compose.yml`.
* One-command setup for complete environment.

---

## 📁 Project Structure

```
virality-engine/
│
├── src/                     # Application source code
├── docs/
│   └── ViralityEngine.postman_collection.json
├── scripts/
│   └── load_test.sh        # Load testing script
├── docker-compose.yml      # Service orchestration
├── pom.xml                 # Maven dependencies
└── README.md
```

---

## 🚀 Getting Started

### 🔧 Prerequisites

Ensure you have the following installed:

* Docker
* Docker Compose
* Java 17 (only if running outside Docker)

---

### 📥 Clone Repository

```bash
git clone <your-repo-url>
cd virality-engine
```

---

### 🐳 Start Infrastructure (PostgreSQL + Redis)

```bash
docker-compose up -d
```

---

### ▶️ Run Application

```bash
./mvnw spring-boot:run
```

---

## 📡 API Endpoints

### 🔥 Trigger Viral Event

```
POST /api/v1/virality/trigger
```

**Description:**
Triggers a viral interaction (e.g., click/share/referral) and updates the count atomically.

---

## 🧪 API Testing

A ready-to-use Postman collection is available:

```
docs/ViralityEngine.postman_collection.json
```

Import it into Postman to quickly test endpoints.

---

## 📈 Load Testing & Validation

To validate concurrency handling and data consistency:

### Run Load Test Script

```bash
./scripts/load_test.sh
```

### What It Does

* Simulates **200+ concurrent requests** using tools like Apache Bench (`ab`)
* Compares:

  * Redis counter value
  * PostgreSQL persisted value

### Expected Outcome

✅ Counts should match exactly
❌ Any mismatch indicates a race condition issue

---

## 🛡️ Concurrency Strategy

* **Redis Atomic Counters (`INCR`)** ensure safe increments
* **Distributed Locks** prevent conflicting writes
* **Separation of concerns**:

  * Redis → real-time updates
  * PostgreSQL → final consistency

---

## 📦 Deployment Notes

* Easily deployable using Docker in any environment
* Scales horizontally with Redis and database tuning
* Suitable for:

  * Referral systems
  * Ad click tracking
  * Social media metrics

---

## ✅ Submission Checklist

* [x] Complete GitHub repository
* [x] Thread-safe implementation using Redis
* [x] Docker Compose setup
* [x] Postman collection for API testing
* [x] Load testing script for concurrency validation

---

## 💡 Future Enhancements

* Rate limiting (to prevent abuse)
* Kafka integration for event streaming
* Horizontal scaling with Redis Cluster
* Observability (Prometheus + Grafana)

---

## 📜 License

This project is open-source and available under the MIT License.

---

## 🤝 Contributing

Pull requests are welcome. For major changes, open an issue first to discuss your ideas.

---

## 🧠 Final Note

This project demonstrates backend engineering principles critical for real-world systems:

* Concurrency control
* Distributed systems design
* Data consistency under load

If your system can handle **virality**, it can handle almost anything.

---
