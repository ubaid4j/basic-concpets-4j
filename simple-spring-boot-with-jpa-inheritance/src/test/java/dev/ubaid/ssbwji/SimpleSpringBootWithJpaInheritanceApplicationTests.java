package dev.ubaid.ssbwji;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(
		properties = {
				"spring.datasource.url=jdbc:tc:postgresql:16.2:///testdb"
		}
)
class SimpleSpringBootWithJpaInheritanceApplicationTests {

	@Test
	void contextLoads() {
	}

}
