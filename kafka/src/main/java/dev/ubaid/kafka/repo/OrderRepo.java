package dev.ubaid.kafka.repo;

import dev.ubaid.kafka.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<Order, String> {
}
