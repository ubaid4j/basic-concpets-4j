package com.ubaid.forj;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;
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

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientSidePreparedStatementCachingTest {

    private final static Logger log = LoggerFactory.getLogger(ClientSidePreparedStatementCachingTest.class);
    private final static Random random = ThreadLocalRandom.current();


    @Container
    private final static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withLogConsumer(new Slf4jLogConsumer(log));

    static {
        SLF4JBridgeHandler.install();
    }


    private DataSource ds;


    @NotNull
    private static HikariConfig getHikariConfig() {
        String url = postgreSQLContainer.getJdbcUrl() + "&user=" + postgreSQLContainer.getUsername() + "&password=" + postgreSQLContainer.getPassword();
        url = url.replace("loggerLevel=OFF", "loggerLevel=trace");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(postgreSQLContainer.getUsername());
        config.setPassword(postgreSQLContainer.getPassword());
        config.addDataSourceProperty("preparedStatementCacheQueries", "0");
        config.addDataSourceProperty("preparedStatementCacheSizeMiB", "0");
        config.setMaximumPoolSize(100);
        config.setIdleTimeout(10000);
        return config;
    }


    @BeforeAll
    void setupDatabase() throws SQLException {
        DriverManager.setLogWriter(new PrintWriter(System.out, true));

        ds = new HikariDataSource(getHikariConfig());
        
        
        Connection connection = ds.getConnection();
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
    void testPreparedStatementCache() throws SQLException {
        Connection connection = ds.getConnection();

        PreparedStatement getPostId1 = connection.prepareStatement("SELECT nextval('post_seq')");

        PreparedStatement postStatement1 = connection.prepareStatement("""
            INSERT INTO post(id, title, version) VALUES (?, ?, ?);
        """);

        ResultSet postIdResultSet = getPostId1.executeQuery();
        postIdResultSet.next();
        int postId = postIdResultSet.getInt(1);
        log.info("postId: {}", postId);

        postStatement1.setInt(1, postId);
        postStatement1.setString(2, "Post #1");
        postStatement1.setInt(3, 0);
        postStatement1.executeUpdate();
        postStatement1.close();

        log("Second query");
        
        PreparedStatement getPostId2 = connection.prepareStatement("SELECT nextval('post_seq')");

        PreparedStatement postStatement2 = connection.prepareStatement("""
            INSERT INTO post(id, title, version) VALUES (?, ?, ?);
        """);

        ResultSet postIdResultSet2 = getPostId2.executeQuery();
        postIdResultSet2.next();
        postId = postIdResultSet2.getInt(1);
        log.info("postId: {}", postId);

        postStatement2.setInt(1, postId);
        postStatement2.setString(2, "Post #1");
        postStatement2.setInt(3, 0);
        postStatement2.executeUpdate();

    }


    void log(String info) {
        log.info("\n\n--------------------------------{}----------------------------------\n\n", info);
    }
}
