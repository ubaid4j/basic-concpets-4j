package dev.ubaid.ssbwj.jpa;

import dev.ubaid.ssbwj.domain.Post;
import dev.ubaid.ssbwj.domain.PostComment;
import dev.ubaid.ssbwj.repository.PostCommentRepository;
import dev.ubaid.ssbwj.repository.PostRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Testcontainers
@SpringBootTest
@ActiveProfiles({
        "test"
})
public class CascadeTest {

    private final static Logger log = LoggerFactory.getLogger(JpaTest.class);
    private final static Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);

    @Container
    private final static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.2")
            .withCommand("postgres", "-c", "log_statement=all")
            .withLogConsumer(logConsumer);

    @DynamicPropertySource
    static void setupPostgresProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    EntityManager entityManager;
    
    @Autowired
    PostCommentRepository postCommentRepository;
    
    @Autowired
    PostRepository postRepository;

    private Post getPost1() {
        Post post = new Post();
        post.setLastModifiedDate(Instant.now());
        post.setCreatedDate(Instant.now());
        post.setLastModifiedBy("system");
        post.setCreatedBy("system");
        post.setVersion(1);
        post.setTitle("Post 2");
        post.setUuid(UUID.randomUUID().toString());
        return post;
    }
    
    private PostComment getDefaultComment() {
        PostComment comment = new PostComment();
        comment.setLastModifiedBy("system");
        comment.setCreatedBy("system");
        comment.setCreatedDate(Instant.now());
        comment.setLastModifiedDate(Instant.now());

        comment.setVersion(1);
        comment.setUuid(UUID.randomUUID().toString());
        comment.setReview("fantastic");
        return comment;
    }
    
    
    @Test
    @Transactional
    @Commit
    void verifySavingPostCommentsWithPost() {
        Post post1 = getPost1();
        PostComment comment1 = getDefaultComment();
        PostComment comment2 = getDefaultComment();
        PostComment comment3 = getDefaultComment();
        
        post1.addPostComment(comment1);
        post1.addPostComment(comment2);
        post1.addPostComment(comment3);
        
        postRepository.save(post1);

        List<PostComment> postComments = postCommentRepository.findAllByPost(post1);
        Assertions.assertEquals(3, postComments.size());
        
    }

    @Test
    @Commit
    void verifyPostIsNotDeletedDueToChildPostComments() {
        Post post1 = getPost1();
        PostComment comment1 = getDefaultComment();
        PostComment comment2 = getDefaultComment();

        post1.addPostComment(comment1);
        post1.addPostComment(comment2);
        
        postRepository.save(post1);

        List<PostComment> postComments = postCommentRepository.findAllByPost(post1);
        Assertions.assertEquals(2, postComments.size());
        
        DataIntegrityViolationException exp = 
                Assertions.assertThrowsExactly(DataIntegrityViolationException.class, () -> postRepository.delete(post1));

        ConstraintViolationException foreignKeyConstraintExp = (ConstraintViolationException) exp.getCause();
        
        String expectedMessage = "update or delete on table \"post\" violates foreign key constraint \"fk_post_comment__post_id\" on table \"post_comment\"";
        String actualMessage = foreignKeyConstraintExp.getMessage();
        
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        List<PostComment> comments = postCommentRepository.findAllByPost(post1);
        Assertions.assertEquals(2, comments.size());
    }
    
    @Test
    @Commit
    void verifyDeletePostComment() {
        Post post1 = getPost1();
        PostComment comment1 = getDefaultComment();
        PostComment comment2 = getDefaultComment();

        post1.addPostComment(comment1);
        post1.addPostComment(comment2);
        postRepository.save(post1);

        List<PostComment> postComments = postCommentRepository.findAllByPost(post1);

        postCommentRepository.delete(postComments.getLast());
        
        List<PostComment> comments = postCommentRepository.findAllByPost(post1);
        Assertions.assertEquals(1, comments.size());
    }


}
