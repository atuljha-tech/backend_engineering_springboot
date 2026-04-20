#!/bin/bash

POST_ID=1
BOT_ID=1

echo "Testing race condition with 200 concurrent bot comments..."
echo "This should result in exactly 100 comments being created"
echo ""

for i in {1..200}; do
  curl -X POST http://localhost:8080/api/posts/$POST_ID/comments \
    -H "Content-Type: application/json" \
    -d "{\"authorId\": $BOT_ID, \"authorType\": \"BOT\", \"content\": \"Comment $i\", \"parentCommentId\": null}" \
    -s -o /dev/null -w "%{http_code}\n" &
done

wait

echo ""
echo "All requests completed. Check the database for comment count."
echo "Expected: Exactly 100 comments with status 201"
echo "Expected: 100 requests with status 429 (Too Many Requests)"
