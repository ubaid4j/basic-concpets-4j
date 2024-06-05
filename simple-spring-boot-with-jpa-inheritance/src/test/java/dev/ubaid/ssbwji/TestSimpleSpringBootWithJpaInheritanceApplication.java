package dev.ubaid.ssbwji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestSimpleSpringBootWithJpaInheritanceApplication {

	public static void main(String[] args) {
		SpringApplication.from(SimpleSpringBootWithJpaInheritanceApplication::main).with(TestSimpleSpringBootWithJpaInheritanceApplication.class).run(args);
	}

}
