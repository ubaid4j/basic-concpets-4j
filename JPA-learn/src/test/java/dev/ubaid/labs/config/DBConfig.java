package dev.ubaid.labs.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.ubaid.labs.domain.User;
import java.util.Objects;
import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class DBConfig {
    
    
    private static EntityManagerFactory entityManagerFactory;
    
    public static EntityManagerFactory getEntityManagerFactory(PostgreSQLContainer<?> postgreSQLContainer) {
        if (Objects.isNull(entityManagerFactory)) {
            entityManagerFactory = sessionFactory(postgreSQLContainer);
        }
        return entityManagerFactory;
    }

    private static SessionFactory sessionFactory(PostgreSQLContainer<?> postgreSQLContainer) {
        return new LocalSessionFactoryBuilder(dataSource(postgreSQLContainer))
            .setProperty("hibernate.hbm2ddl.auto", "update")
            .addAnnotatedClass(User.class)
            .addPackage("dev.ubaid.labs.domain")
            .buildSessionFactory();
    }
    
    private static DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
        config.setDriverClassName("org.postgresql.Driver");
        config.setUsername(postgreSQLContainer.getUsername());
        config.setPassword(postgreSQLContainer.getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(100);
        config.setIdleTimeout(10000);
        return new HikariDataSource(config);
    }
}
