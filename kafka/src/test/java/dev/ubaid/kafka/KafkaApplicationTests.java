package dev.ubaid.kafka;

import static org.awaitility.Awaitility.await;

import dev.ubaid.kafka.domain.Order;
import dev.ubaid.kafka.domain.enumumeration.OrderStatus;
import dev.ubaid.kafka.repo.OrderRepo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@TestPropertySource(
        properties = {
               "spring.datasource.url=jdbc:tc:postgresql:16.3:///testdb",
                "spring.jpa.generate-ddl=true"
        }
)
@Testcontainers
class KafkaApplicationTests {
    
    @Container
    static final KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.6.1")
    );
    
    @DynamicPropertySource
    static void setupKafkaServer(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @Autowired
    private OrderRepo orderRepo;
    
    @Test
    void shouldHandleOrderProcessEvent() {
        Order order = new Order(null, "o1", UUID.randomUUID().toString(), OrderStatus.CREATED, Instant.now());
        orderRepo.save(order);
        
        kafkaTemplate.send("order-under-process", order);
        
        final String orderId = order.getUuid();
        
        await()
                .pollInterval(Duration.ofSeconds(3))
                .atMost(Duration.ofSeconds(10))
                .until(() -> {
                    Order expectedOrder = orderRepo.findById(orderId).orElseThrow();
                    return expectedOrder.getOrderStatus();
                }, Matchers.is(OrderStatus.DELIVERED));
    }

}
