package dev.ubaid.ssbwj.jpa;

import dev.ubaid.ssbwj.domain.Post;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class JpaTest {
    
    @Autowired
    EntityManager entityManager;
    
    @Test
    void getPosts() {
        final int id = 1;
        List<Post> posts = entityManager.createQuery("""
            SELECT DISTINCT p
            FROM Post p
            JOIN FETCH p.postComments
            WHERE p.id BETWEEN :id AND :id+1""", Post.class)
            .setParameter("id", id)
            .getResultList();

        Assertions.assertEquals(1, posts.size());
    }
    
}
