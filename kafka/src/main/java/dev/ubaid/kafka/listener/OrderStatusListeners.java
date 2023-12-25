package dev.ubaid.kafka.listener;

import dev.ubaid.kafka.domain.Order;
import dev.ubaid.kafka.domain.enumumeration.OrderStatus;
import dev.ubaid.kafka.repo.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class OrderStatusListeners {

    private final Logger log = LoggerFactory.getLogger(OrderStatusListeners.class);
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderRepo orderRepo;

    public OrderStatusListeners(KafkaTemplate<String, Object> kafkaTemplate, OrderRepo orderRepo) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderRepo = orderRepo;
    }

    @KafkaListener(topics = "order-under-process")
    void onOrderCreation(Order order) {
        log.info("########### on order {} order-under-process", order.getUuid());
        
        order = orderRepo.findById(order.getUuid()).orElseThrow();
        
        log.info("updating order status to {}", OrderStatus.UNDER_PROCESS);
        order.setOrderStatus(OrderStatus.UNDER_PROCESS);
        orderRepo.save(order);
        
        kafkaTemplate.send("order-process", order);
        
    }

    @KafkaListener(topics = "order-process")
    void onOrderUnderProcessing(Order order) {
        log.info("######### on order {} process", order.getUuid());

        order = orderRepo.findById(order.getUuid()).orElseThrow();
        log.info("updating order status to {}", OrderStatus.PROCESSED);
        order.setOrderStatus(OrderStatus.PROCESSED);
        orderRepo.save(order);

        kafkaTemplate.send("order-ready", order);
    }



    @KafkaListener(topics = "order-ready")
    void onOrderProcessing(Order order) {
        log.info("######### on order {} ready for delivery", order.getUuid());

        order = orderRepo.findById(order.getUuid()).orElseThrow();
        log.info("updating order status to {}", OrderStatus.READY_FOR_DELIVERY);
        order.setOrderStatus(OrderStatus.READY_FOR_DELIVERY);
        orderRepo.save(order);

        kafkaTemplate.send("delivery-in-progress", order);
    }
    
    @KafkaListener(topics = "delivery-in-progress")
    void onOrderReadyForDelivery(Order order) {
        log.info("######### on order {} delivery in progress", order.getUuid());

        order = orderRepo.findById(order.getUuid()).orElseThrow();
        log.info("updating order status to {}", OrderStatus.DELIVERY_IN_PROGRESS);
        order.setOrderStatus(OrderStatus.DELIVERY_IN_PROGRESS);
        orderRepo.save(order);

        kafkaTemplate.send("order-delivered", order);
    }
    
    @KafkaListener(topics = "order-delivered")
    void onOrderDeliveryInProgress(Order order) {
        log.info("######### on order {} delivered", order.getUuid());

        order = orderRepo.findById(order.getUuid()).orElseThrow();
        log.info("updating order status to {}", OrderStatus.DELIVERED);
        order.setOrderStatus(OrderStatus.DELIVERED);
        orderRepo.save(order);
    }
}
