package com.ubaid.forj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class JdbcConnectionTest {

    private final static Logger logger = LoggerFactory.getLogger(JdbcConnectionTest.class);
    
    @Container
    private final static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @NotNull
    private static HikariConfig getHikariConfig() {
        String url = postgreSQLContainer.getJdbcUrl() + "&user=" + postgreSQLContainer.getUsername() + "&password=" + postgreSQLContainer.getPassword();
        url = url.replace("loggerLevel=OFF", "loggerLevel=trace");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(postgreSQLContainer.getUsername());
        config.setPassword(postgreSQLContainer.getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(100);
        config.setIdleTimeout(10000);
        return config;
    }
    
    @Test
    public void testConnectionFromDriverManager() throws SQLException {
        
        //get connection from DriverManager
        String url = postgreSQLContainer.getJdbcUrl() + "&user=" + postgreSQLContainer.getUsername() + "&password=" + postgreSQLContainer.getPassword();
        url = url.replace("loggerLevel=OFF", "loggerLevel=trace");
        DriverManager.setLogWriter(new PrintWriter(System.out, true));
        Connection connection = DriverManager.getConnection(url);
        assertNotNull(connection, "connection should not null");
        logger.debug("Schema Name: {}", connection.getSchema());
        
        
        //test connection
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        int one = -1;
        while (resultSet.next()) {
            one = resultSet.getInt(1);
        }
        assertNotNull(resultSet, "result set should not return");
        assertEquals(1, one, "result set should equal to 1");
        
    }
    
    @Test
    public void testConnectionUsingPGSimpleDataSource() throws Exception {
        String url = postgreSQLContainer.getJdbcUrl() + "&user=" + postgreSQLContainer.getUsername() + "&password=" + postgreSQLContainer.getPassword();
        url = url.replace("loggerLevel=OFF", "loggerLevel=trace");

        // TODO: 5/29/22 set logging for both PGSimpleDataSource and then DriverManager 
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setLogWriter(new PrintWriter(System.out, true));
        ds.setUrl(url);
        Connection connection = ds.getConnection();
        assertNotNull(connection, "connection should not null");
        logger.debug("Schema name: {}", connection.getSchema());
        
        //test connection
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        int one = -1;
        while (resultSet.next()) {
            one = resultSet.getInt(1);
        }
        assertNotNull(resultSet, "result set should not return");
        assertEquals(1, one, "result set should equal to 1");
        
    }
    
    @Test
    public void testConnectionUsingHikariConnectionPoolDataSource() throws Exception {
        HikariConfig config = getHikariConfig();

        try(var ds = new HikariDataSource(config)) {
            Connection connection = ds.getConnection();
            assertNotNull(connection, "connection should not null");
            logger.debug("Schema name: {}", connection.getSchema());

            //test connection
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            int one = -1;
            while (resultSet.next()) {
                one = resultSet.getInt(1);
            }
            assertNotNull(resultSet, "result set should not return");
            assertEquals(1, one, "result set should equal to 1");
        }
    }
    @Test
    public void testConnectionUsingHikariConnectionPoolDataSourceWithAutoCloseConnection() throws Exception {
        HikariConfig config = getHikariConfig();

        try(var ds = new HikariDataSource(config);
            var con = ds.getConnection()) {
            
            assertNotNull(con, "connection should not null");
            logger.debug("Schema name: {}", con.getSchema());
        }
    }
    
    @Test
    public void testConnectionUsingCommonDBCP2() throws Exception {
        String url = postgreSQLContainer.getJdbcUrl() + "&user=" + postgreSQLContainer.getUsername() + "&password=" + postgreSQLContainer.getPassword();
        url = url.replace("loggerLevel=OFF", "loggerLevel=trace");

        try (var ds = new BasicDataSource()) {
            ds.setUrl(url);
            ds.setUsername(postgreSQLContainer.getUsername());
            ds.setPassword(postgreSQLContainer.getPassword());

            Connection connection = ds.getConnection();
            assertNotNull(connection, "connection should not null");
            logger.debug("Schema name: {}", connection.getSchema());

            //test connection
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            int one = -1;
            while (resultSet.next()) {
                one = resultSet.getInt(1);
            }
            assertNotNull(resultSet, "result set should not return");
            assertEquals(1, one, "result set should equal to 1");
        }
    }
    
    @Test
    public void test100ConcurrentConnectionsHikariCP() throws Exception {
        HikariConfig hikariConfig = getHikariConfig();
        try (var ds = new HikariDataSource(hikariConfig)) {
            ExecutorService service = Executors.newCachedThreadPool();
            for (int i = 0; i < 100; i++) {
                service.submit(() -> {
                    try {
                        try (var connection = ds.getConnection()) {
                            assertNotNull(connection, "connection should not null");
                            logger.debug("Schema name: {}", connection.getSchema());

                            PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1");
                            ResultSet resultSet = preparedStatement.executeQuery();
                            int one = -1;
                            while (resultSet.next()) {
                                one = resultSet.getInt(1);
                            }
                            assertNotNull(resultSet, "result set should not return");
                            assertEquals(1, one, "result set should equal to 1");
                        }
                    } catch (SQLException e) {
                        logger.error("sql exception: ", e);
                        throw new RuntimeException(e);
                    }
                });
            }
            service.shutdown();
            Thread.sleep(3000);
        }
    }
    
    
}
