package com.assignment.viralityengine.service;

import com.assignment.viralityengine.dto.CreateCommentRequest;
import com.assignment.viralityengine.dto.CreatePostRequest;
import com.assignment.viralityengine.dto.LikePostRequest;
import com.assignment.viralityengine.entity.Bot;
import com.assignment.viralityengine.entity.Comment;
import com.assignment.viralityengine.entity.Post;
import com.assignment.viralityengine.entity.User;
import com.assignment.viralityengine.repository.BotRepository;
import com.assignment.viralityengine.repository.CommentRepository;
import com.assignment.viralityengine.repository.PostRepository;
import com.assignment.viralityengine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BotRepository botRepository;

    @Autowired
    private ViralityService viralityService;

    @Autowired
    private GuardrailService guardrailService;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Post createPost(CreatePostRequest request) {
        validateAuthor(request.getAuthorId(), request.getAuthorType());

        Post post = new Post();
        post.setAuthorId(request.getAuthorId());
        post.setAuthorType(request.getAuthorType());
        post.setContent(request.getContent());

        return postRepository.save(post);
    }

    @Transactional
    public Comment addComment(Long postId, CreateCommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        validateAuthor(request.getAuthorId(), request.getAuthorType());

        int depth = 0;
        Long parentCommentId = request.getParentCommentId();

        if (parentCommentId != null) {
            Comment parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent comment not found"));
            depth = parentComment.getDepthLevel() + 1;
        }

        if ("BOT".equalsIgnoreCase(request.getAuthorType())) {
            if (!guardrailService.canBotReplyToPost(postId)) {
                throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Bot reply limit exceeded for this post");
            }

            if (!guardrailService.isDepthAllowed(depth)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment depth limit exceeded");
            }

            Long humanAuthorId = getHumanAuthorId(post, parentCommentId);
            if (humanAuthorId != null && guardrailService.isCooldownActive(request.getAuthorId(), humanAuthorId)) {
                throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Bot is in cooldown period for this user");
            }

            guardrailService.incrementBotReplyCount(postId);
            viralityService.incrementViralityForBotReply(postId);

            if (humanAuthorId != null) {
                guardrailService.setCooldown(request.getAuthorId(), humanAuthorId);
                Bot bot = botRepository.findById(request.getAuthorId()).orElse(null);
                String botName = bot != null ? bot.getName() : "Unknown";
                notificationService.handleBotInteraction(humanAuthorId, botName, "replied to your post");
            }
        } else if ("USER".equalsIgnoreCase(request.getAuthorType())) {
            viralityService.incrementViralityForHumanComment(postId);
        }

        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setParentCommentId(parentCommentId);
        comment.setAuthorId(request.getAuthorId());
        comment.setAuthorType(request.getAuthorType());
        comment.setContent(request.getContent());
        comment.setDepthLevel(depth);

        return commentRepository.save(comment);
    }

    @Transactional
    public void likePost(Long postId, LikePostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        viralityService.incrementViralityForHumanLike(postId);
    }

    private void validateAuthor(Long authorId, String authorType) {
        if ("USER".equalsIgnoreCase(authorType)) {
            userRepository.findById(authorId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        } else if ("BOT".equalsIgnoreCase(authorType)) {
            botRepository.findById(authorId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bot not found"));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid author type");
        }
    }

    private Long getHumanAuthorId(Post post, Long parentCommentId) {
        if ("USER".equalsIgnoreCase(post.getAuthorType())) {
            return post.getAuthorId();
        }

        if (parentCommentId != null) {
            Comment parentComment = commentRepository.findById(parentCommentId).orElse(null);
            if (parentComment != null && "USER".equalsIgnoreCase(parentComment.getAuthorType())) {
                return parentComment.getAuthorId();
            }
        }

        return null;
    }

}
