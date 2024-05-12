package dev.ubaid.labs;

import com.github.javafaker.Faker;
import com.querydsl.jpa.impl.JPAQuery;
import dev.ubaid.labs.config.DBConfig;
import dev.ubaid.labs.domain.QUser;
import dev.ubaid.labs.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@TestInstance(Lifecycle.PER_CLASS)
@Testcontainers
public class GeneralTest {

    @Container
    private final static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.3");
    
    EntityManager em;
    Faker faker = new Faker();
    
    List<User> expectedUsers;
    
    @BeforeAll
    void setup() {
        EntityManagerFactory factory = DBConfig.getEntityManagerFactory(postgreSQLContainer);
        em = factory.createEntityManager();
        em.getTransaction().begin();
        List<User> users = getUsers();
        users.forEach(u -> em.persist(u));
        em.getTransaction().commit();
        
        expectedUsers = users
            .stream()
            .filter(u -> u.getEmail().contains("yopmail.com"))
            .filter(u -> u.getFirstName().contains("b"))
            .sorted((u1, u2) -> StringUtils.compare(u1.getLastName(), u2.getLastName()))
            .toList();
        
        log.debug("Expected Result: {}", expectedUsers);
    }
    
    @BeforeEach
    void beforeEach() {
        
    }
    
    @Test
    void fetchDataUsingJPQL() {
        em.getTransaction().begin();
        TypedQuery<User> query = em.createQuery("""
            SELECT u FROM User u
            WHERE u.email LIKE '%yopmail.com' AND u.firstName LIKE ?1
            ORDER BY u.lastName
         """, User.class)
        .setParameter(1, "%b%");
        
        List<User> users = query.getResultList();
        log.debug("Users: {}", users);
        em.getTransaction().commit();

        Assertions.assertEquals(expectedUsers, users);
    }
    
    
    @Test
    void fetchDataUsingCriteriaAPI() {
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr
            .select(root)
            .where(
                cb.and(
                    cb.like(root.get("email"), "%yopmail.com"),
                    cb.like(root.get("firstName"), cb.parameter(String.class, "name"))
                )
            );
        cr.orderBy(cb.asc(root.get("lastName")));
        
        TypedQuery<User> query = em.createQuery(cr);
        query.setParameter("name", "%b%");
        
        List<User> users = query.getResultList();
        log.debug("Users: {}", users);
        em.getTransaction().commit();

        Assertions.assertEquals(expectedUsers, users);
    }
    
    @Test
    void fetchDataUsingQueryDSL() {
        em.getTransaction().begin();
        JPAQuery<User> jpaQuery = new JPAQuery<>(em);
        QUser user = QUser.user;
        
        List<User> users = jpaQuery
            .select(user)
            .from(user)
            .where(
                 user.email.contains("yopmail.com")
                .and(user.firstName.contains("b")))
            .orderBy(user.lastName.asc())
            .fetch();
        
        em.getTransaction().commit();
        
        Assertions.assertEquals(expectedUsers, users);
    }
    
    
    @AfterAll
    void destroy() {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM User u WHERE u.id > 0").executeUpdate();
//        em.createNativeQuery("alter sequence jpa_user_seq restart").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
    
    List<User> getUsers() {
        return IntStream.
            range(0, 100)
            .mapToObj(i -> new User(null, faker.name().firstName(), faker.name().lastName(), faker.name().username() + "@yopmail.com"))
            .collect(Collectors.toList());
    }
}
