package dev.ubaid.ssbwj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(
        properties = {
                "spring.datasource.url=jdbc:tc:postgresql:16.2:///testdb"
        }
)
class SimpleSpringBootWithJpaApplicationTests {

    @Autowired
    private ConfigurableEnvironment env;

    @Test
    void contextLoads() {
    }
}
