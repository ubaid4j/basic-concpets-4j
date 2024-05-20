package dev.ubaid.ssbwj.jpa;

import dev.ubaid.ssbwj.domain.Event;
import dev.ubaid.ssbwj.domain.Post;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Testcontainers
@SpringBootTest
@ActiveProfiles({
        "test"
})
public class JpaTest {

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
    
    @Test
    void getPosts() {
        final int id = 1;
        List<Post> posts = entityManager.createQuery("""
            SELECT DISTINCT p
            FROM Post p
            JOIN FETCH p.postComments
            JOIN FETCH p.postDetail
            WHERE p.id BETWEEN :id AND :id+1""", Post.class)
            .setParameter("id", id)
            .getResultList();

        Assertions.assertEquals(1, posts.size());
        
        Assertions.assertEquals("post1", posts.getFirst().getTitle());
        Assertions.assertEquals("That is great post", new ArrayList<>(posts.getFirst().getPostComments()).getFirst().getReview());
        Assertions.assertEquals("Post 1 description", posts.getFirst().getPostDetail().getDescription());
    }
    
    @Test
    @Transactional
    @Commit
    void createPost() {
        Post post = new Post();
        post.setLastModifiedDate(Instant.now());
        post.setCreatedDate(Instant.now());
        post.setLastModifiedBy("system");
        post.setCreatedBy("system");
        post.setVersion(1);
        post.setTitle("Post 2");
        post.setUuid(UUID.randomUUID().toString());
        entityManager.persist(post);
    }
    
    
    @Test
    @Transactional
    @Commit
    void createEvent() {
        Event event = new Event("192.168.0.1");
        entityManager.persist(event);
    }
    
}
