package com.ubaid.forj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
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
    
    
}
