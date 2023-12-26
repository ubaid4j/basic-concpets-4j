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

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BatchingStatementsTest {
    
    private final static Logger log = LoggerFactory.getLogger(BatchingStatementsTest.class);
    private final static Random random = ThreadLocalRandom.current();
    
    
    @Container
    private final static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withLogConsumer(new Slf4jLogConsumer(log));

    static {
        SLF4JBridgeHandler.install();
    }


    private String dbURL;
    
    @BeforeAll
    void setupDatabase() throws SQLException {
        dbURL = postgreSQLContainer.getJdbcUrl() + "&user=" + postgreSQLContainer.getUsername() + "&password=" + postgreSQLContainer.getPassword();
        dbURL = dbURL.replace("loggerLevel=OFF", "loggerLevel=trace");
        DriverManager.setLogWriter(new PrintWriter(System.out, true));

        Connection connection = DriverManager.getConnection(dbURL);
        Assertions.assertNotNull(connection, "connection should be not null");
        log.info("Connection acquired");
        log.info("Schema Name: {}", connection.getSchema());

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
            )
        """);
        
        createPostTable.executeUpdate();
        
        PreparedStatement postSeq = connection.prepareStatement("""
            CREATE SEQUENCE post_seq
            AS int
            INCREMENT BY 500
            MINVALUE 0
            START WITH 1
            OWNED BY post.id;
        """);
        postSeq.execute();
        
        
        PreparedStatement createPostCommentTable = connection.prepareStatement("""
            CREATE TABLE post_comment (
                id serial primary key ,
                review varchar(255),
                version int,
                post_id int,
                constraint fk_post foreign key (post_id) references post(id)
            )
        """);
        
        createPostCommentTable.executeUpdate();


        final ResultSet postTableQuery = connection.getMetaData().getTables(null, null, "post", null);
        Assertions.assertTrue(postTableQuery.next(), "table `post` should exists");

        final ResultSet postCommentTableQuery = connection.getMetaData().getTables(null, null, "post_comment", null);
        Assertions.assertTrue(postCommentTableQuery.next(), "table `post_comment` should exists");
    }
    
    @Test
    void addPostAdCommentWithoutBatch() throws SQLException {
        Connection connection = DriverManager.getConnection(dbURL);
        Statement statement = connection.createStatement();

        log.info("\n\n--------------------------------Starting----------------------------------\n\n");

        statement.executeUpdate("""
            INSERT into post(title, version, id) VALUES ('Post No 1', 0, default);
        """);

        statement.executeUpdate("""
            INSERT INTO post_comment(post_id, review, version, id) 
            VALUES (1, 'post comment 1.1', 0, default);
        """);
    }
    
    @Test
    void addPostAndCommentUsingBatch() throws SQLException {
        Connection connection = DriverManager.getConnection(dbURL);
        Statement statement = connection.createStatement();
        
        log.info("\n\n--------------------------------Starting----------------------------------\n\n");
        
        statement.addBatch("""
            INSERT into post(title, version, id) VALUES ('Post No 1', 0, 564);
        """);
        
        statement.addBatch("""
            INSERT INTO post_comment(post_id, review, version, id) 
            VALUES (564, 'post comment 1.1', 0, default);
        """);
        
        int[] updateCounts = statement.executeBatch();
        
        log.info("update counts: {}", updateCounts);
        
        Assertions.assertArrayEquals(new int[] {1, 1}, updateCounts, "should be {1, 1}");
    }

    @Test
    void addPostAndCommentUsingBatchWithPreparedStatement() throws SQLException {
        Connection connection = DriverManager.getConnection(dbURL);
        PreparedStatement postStatement = connection.prepareStatement("""
            INSERT INTO post(title, version, id) VALUES (?, ?, ?);
        """);
        
        log.info("\n\n--------------------------------Starting----------------------------------\n\n");

        postStatement.setString(1, "Post no 1");
        postStatement.setInt(2, 0);
        postStatement.setInt(3, random.nextInt());
        postStatement.addBatch();
        
        postStatement.setString(1, "Post no 2");
        postStatement.setInt(2, 0);
        postStatement.setInt(3, random.nextInt());
        postStatement.addBatch();
        
        int[] updateCounts = postStatement.executeBatch();
        Assertions.assertArrayEquals(new int[] {1, 1}, updateCounts, "update count should be {1, 1}");
        
        
        PreparedStatement deletePost = connection.prepareStatement("""
            DELETE FROM post WHERE version=0;
        """);

        log.info("\n\n--------------------------------deleting post----------------------------------\n\n");
        int rowsAffected = deletePost.executeUpdate();
        Assertions.assertEquals(2, rowsAffected, "two rows should be deleted");
    }
    
    @Test
    void addPostWithIdFromSeq() throws SQLException {
        Connection connection = DriverManager.getConnection(dbURL);
        
        PreparedStatement getPostId = connection.prepareStatement("SELECT nextval('post_seq')");
        
        
        PreparedStatement postStatement = connection.prepareStatement("""
            INSERT INTO post(id, title, version) VALUES (?, ?, ?);
        """);
        
        log("starting");
        ResultSet postIdResultSet = getPostId.executeQuery();
        postIdResultSet.next();
        int postId = postIdResultSet.getInt(1);
        log.info("postId: {}", postId);
        
        postStatement.setInt(1, postId);
        postStatement.setString(2, "Post #1");
        postStatement.setInt(3, 0);
        
        postStatement.executeUpdate();
    }
    
    
    @AfterEach
    void afterEach() throws SQLException {
        log("After Each starts");
        Connection connection = DriverManager.getConnection(dbURL);
        PreparedStatement deletePostComment = connection.prepareStatement("TRUNCATE post_comment");
        deletePostComment.execute();
        
        PreparedStatement deletePost = connection.prepareStatement("TRUNCATE post CASCADE");
        deletePost.execute();
        
        log("After Each ends");
    }
    
    void log(String info) {
        log.info("\n\n--------------------------------{}----------------------------------\n\n", info);
    }
}
