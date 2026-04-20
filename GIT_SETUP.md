# Git Setup and Submission Guide

## Step 1: Initialize Git Repository

```bash
git init
```

## Step 2: Add All Files

```bash
git add .
```

## Step 3: Create Initial Commit

```bash
git commit -m "Backend Engineering Assignment: Virality Engine

- Implemented Spring Boot 3.2 microservice with PostgreSQL and Redis
- Created JPA entities: User, Bot, Post, Comment
- Implemented REST endpoints: POST /api/posts, /api/posts/{id}/comments, /api/posts/{id}/like
- Built Redis-based virality scoring system (Bot Reply +1, Human Like +20, Human Comment +50)
- Implemented atomic locks: Horizontal Cap (100 bot replies), Vertical Cap (20 depth), Cooldown (10 min)
- Created notification engine with smart batching and 15-minute throttling
- Added CRON scheduler for batch notification processing
- Ensured thread safety using Redis atomic operations (INCR, SETEX, EXISTS)
- Tested race conditions with 200 concurrent requests (exactly 100 succeed)
- Maintained stateless architecture for horizontal scalability
- Included Docker Compose for PostgreSQL and Redis
- Added Postman collection for API testing
- Created test scripts for race condition validation"
```

## Step 4: Create GitHub Repository

1. Go to https://github.com/new
2. Create a new repository named `virality-engine-backend`
3. Do NOT initialize with README (we already have one)
4. Copy the repository URL

## Step 5: Add Remote and Push

```bash
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/virality-engine-backend.git
git push -u origin main
```

## Step 6: Verify Repository

Visit your GitHub repository and verify:
- [ ] All files are present
- [ ] README.md displays correctly
- [ ] No .class or target/ files (excluded by .gitignore)
- [ ] docker-compose.yml is present
- [ ] postman_collection.json is present

## Step 7: Create Release (Optional but Recommended)

```bash
git tag -a v1.0.0 -m "Assignment Submission v1.0.0"
git push origin v1.0.0
```

## Step 8: Prepare Submission

Copy this text for your submission:

```
Assignment: Backend Engineering - Virality Engine
Name: [Your Name]
Tech Stack: Java 17, Spring Boot 3.2, PostgreSQL 15, Redis 7

GitHub Repository: https://github.com/YOUR_USERNAME/virality-engine-backend

Key Features:
✓ Thread-safe atomic operations using Redis
✓ Race condition handling (tested with 200 concurrent requests)
✓ Stateless architecture (horizontally scalable)
✓ Event-driven notification system with smart batching
✓ Comprehensive guardrails (horizontal, vertical, cooldown caps)
✓ Complete API with validation and error handling

Setup Instructions:
1. docker-compose up -d
2. mvn spring-boot:run
3. ./setup-test-data.sh
4. Import postman_collection.json

Test Race Conditions:
./test-race-condition.sh

Expected Result: Exactly 100 bot comments created, 100 requests return 429.

Documentation:
- README.md: Complete setup and thread safety explanation
- QUICKSTART.md: Quick start guide
- postman_collection.json: API testing collection
- docker-compose.yml: Infrastructure setup

All code files contain zero comments as requested.
```

## Alternative: Create ZIP Archive

If you need to submit a ZIP file:

```bash
git archive --format=zip --output=virality-engine-submission.zip HEAD
```

Or without git:

```bash
zip -r virality-engine-submission.zip . -x "*.git*" "target/*" ".idea/*" "*.iml"
```

## Troubleshooting

### If you need to remove files from git:

```bash
git rm --cached filename
git commit -m "Remove file"
```

### If you need to update .gitignore:

```bash
git rm -r --cached .
git add .
git commit -m "Update .gitignore"
```

### If you need to amend the last commit:

```bash
git add .
git commit --amend --no-edit
git push -f origin main
```

## Final Checklist Before Push

- [ ] All Java files compile without errors
- [ ] No comments in any .java files
- [ ] docker-compose.yml works
- [ ] README.md is complete
- [ ] postman_collection.json is valid JSON
- [ ] .gitignore excludes target/ and .idea/
- [ ] Test scripts have execute permissions (chmod +x *.sh)

## After Pushing

1. Visit your GitHub repository
2. Copy the repository URL
3. Go to the submission platform
4. Paste the URL in "Assignment link" field
5. Add note: "Please find my assignment attached"
6. Click Submit

Done! 🚀
