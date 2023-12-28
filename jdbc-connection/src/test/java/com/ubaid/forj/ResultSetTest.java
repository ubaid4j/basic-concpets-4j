package com.ubaid.forj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Testcontainers
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class ResultSetTest {
    
    private final static Logger log = LoggerFactory.getLogger(ResultSetTest.class);
    
    @Container
    private final static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withLogConsumer(new Slf4jLogConsumer(log));
    
    static {
        SLF4JBridgeHandler.install();
    }
    
    private Connection connection;
    
    @BeforeAll
    void setupDatabase() throws SQLException {
        final String dbURL = (postgresContainer.getJdbcUrl() + "&user=" + postgresContainer.getUsername() + "&password=" + postgresContainer.getPassword())
                .replace("loggerLevel=OFF", "loggerLevel=trace");
        DriverManager.setLogWriter(new PrintWriter(System.out, true));
        connection = DriverManager.getConnection(dbURL);

        Assertions.assertNotNull(connection, "connection should be not null");

        PreparedStatement setLogs = connection.prepareStatement("""
            ALTER DATABASE test
            SET log_statement = 'all';
        """);
        
        setLogs.execute();
        
        PreparedStatement createPostTable = connection.prepareStatement("""
            CREATE TABLE post (
                id      serial primary key,
                title   varchar(255),
                version int
            );
        """);
        
        createPostTable.executeUpdate();

        PreparedStatement createPost = connection.prepareStatement("""
               INSERT INTO post(title, version, id) VALUES (?, ?, ?);
        """);
        
        for (int i = 1; i < 1001; i++) {
            createPost.setString(1, "post number %d".formatted(i));
            createPost.setInt(2, 0);
            createPost.setInt(3, i);
            createPost.addBatch();
            
            if (i%100 == 0) {
                final int[] updateCount = createPost.executeBatch();
                
                int[] expectedCount = new int[100];
                Arrays.fill(expectedCount, 1);
                
                log.info("update count: {}", updateCount);
                Assertions.assertArrayEquals(expectedCount, updateCount, "Update count should be same");
            }
        }
    }
    
    record Post(String title, Integer version, Integer id) {
        static Post from(ResultSet resultSet) throws SQLException {
            String title = resultSet.getString("title");
            Integer version = resultSet.getInt("version");
            Integer id = resultSet.getInt("id");
            return new Post(title, version, id);
        }
    }
    
    @Test
    void testFetchSize() throws Exception {
        connection.setAutoCommit(false);
        PreparedStatement getAllPosts = connection.prepareStatement("""
            SELECT * FROM post;
        """);
        getAllPosts.setFetchSize(100);
        
        ResultSet resultSet = getAllPosts.executeQuery();

        Assertions.assertEquals(100, resultSet.getFetchSize(), "Fetch size should be 100");
        
        List<Post> posts = new ArrayList<>();
        while(resultSet.next()) {
            posts.add(Post.from(resultSet));            
        }
        log.info("all posts: {}", posts);
    }
    
    @Test
    void testResultSetSize() throws Exception {
        PreparedStatement getFirst2Posts = connection.prepareStatement("""
            SELECT * FROM post ORDER BY id DESC LIMIT 2;
        """);
        
        ResultSet resultSet = getFirst2Posts.executeQuery();
        
        List<Post> posts = new ArrayList<>();
        while(resultSet.next()) {
            posts.add(Post.from(resultSet));
        }
        
        Assertions.assertEquals(2, posts.size(), "size should be two");
        Assertions.assertEquals(new Post("post number 1000", 0, 1000), posts.stream().findFirst().orElseThrow(), "Should get the latest post");
        
        log.info("all posts: {}", posts);
        
    }
    
    @AfterEach
    void afterEach() throws Exception {
        connection.setAutoCommit(true);
    }
}
