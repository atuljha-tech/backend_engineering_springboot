#!/bin/bash

echo "Creating test user..."
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username": "test_user", "isPremium": false}'

echo ""
echo "Creating test bot..."
curl -X POST http://localhost:8080/api/bots \
  -H "Content-Type: application/json" \
  -d '{"name": "TestBot", "personaDescription": "A test bot"}'

echo ""
echo "Creating test post..."
curl -X POST http://localhost:8080/api/posts \
  -H "Content-Type: application/json" \
  -d '{"authorId": 1, "authorType": "USER", "content": "Test post for race condition"}'

echo ""
echo "Test data created successfully!"
