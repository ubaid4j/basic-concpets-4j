package dev.ubaid.ssbwj.jdbc;

import dev.ubaid.ssbwj.domain.Post;
import dev.ubaid.ssbwj.domain.PostComment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
@TestPropertySource(
        properties = {
                "spring.datasource.url=jdbc:tc:postgresql:16.2:///testdb"
        }
)
public class JDBCTest {

    static {
//        SLF4JBridgeHandler.install();
    }


    @Autowired
    DataSource ds;
    
    @Test
    void getPosts() throws SQLException {
        String getPostSQL = """
                SELECT *
                FROM post AS p
                JOIN post_comment AS pc ON p.id = pc.post_id
                WHERE
                p.id BETWEEN ? AND ? + 1
                """;
        PreparedStatement statement = ds.getConnection().prepareStatement(getPostSQL);
        
        final int id = 1;
        statement.setInt(1, id);
        statement.setInt(2, id);

        ResultSet resultSet = statement.executeQuery();
        List<Post> posts = toPosts(resultSet);

        Assertions.assertEquals(1, posts.size());
        Assertions.assertEquals("post1", posts.getFirst().getTitle());
    }
    
    private List<Post> toPosts(ResultSet resultSet) throws SQLException {
        Map<Long, Post> postMap = new LinkedHashMap<>();
        while (resultSet.next()) {
            Long postId = resultSet.getLong(1);
            Post post = postMap.get(postId);
            if (Objects.isNull(post)) {
                post = new Post();
                postMap.put(postId, post);
                post.setTitle(resultSet.getString(3));
                post.setVersion(resultSet.getInt(4));
            }

            PostComment comment = new PostComment();
            comment.setId(resultSet.getLong(9));
            comment.setReview(resultSet.getString(11));
            comment.setVersion(resultSet.getInt(12));
            post.addPostComment(comment);
        }
        return new ArrayList<>(postMap.values());
    }
}
