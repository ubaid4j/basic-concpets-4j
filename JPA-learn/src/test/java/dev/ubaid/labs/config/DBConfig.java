package dev.ubaid.labs.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.ubaid.labs.domain.User;
import java.util.Objects;
import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

public class DBConfig {

    private static EntityManagerFactory entityManagerFactory;
    
    public static EntityManagerFactory getEntityManagerFactory() {
        if (Objects.isNull(entityManagerFactory)) {
            entityManagerFactory = sessionFactory();
        }
        return entityManagerFactory;
    }

    private static SessionFactory sessionFactory() {
        return new LocalSessionFactoryBuilder(dataSource())
            .setProperty("hibernate.hbm2ddl.auto", "none")
            .addAnnotatedClass(User.class)
            .addPackage("dev.ubaid.labs.domain")
            .buildSessionFactory();
    }
    
    private static DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5600/test");
        config.setDriverClassName("org.postgresql.Driver");
        config.setUsername("test");
        config.setPassword("test");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(100);
        config.setIdleTimeout(10000);
        return new HikariDataSource(config);
    }
}
