package dev.ubaid.kafka.listener;

import dev.ubaid.kafka.domain.Order;
import dev.ubaid.kafka.repo.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusListeners {

    private final Logger log = LoggerFactory.getLogger(OrderStatusListeners.class);
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderRepo orderRepo;

    public OrderStatusListeners(KafkaTemplate<String, Object> kafkaTemplate, OrderRepo orderRepo) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderRepo = orderRepo;
    }

    @KafkaListener(topics = "order-create", groupId = "order-create")
    void onOrderCreation(Order order) {
        log.info("{} ");
    }
    
    @KafkaListener(topics = "order-process", groupId = "order-process")
    void onOrderProcessing() {
        
    }
    
    @KafkaListener(topics = "order-ready", groupId = "order-ready")
    void onOrderReadyForDelivery() {
        
    }
    
    @KafkaListener(topics = "order-delivered", groupId = "order-delivered")
    void onOrderDelivered() {
        
    }
}
