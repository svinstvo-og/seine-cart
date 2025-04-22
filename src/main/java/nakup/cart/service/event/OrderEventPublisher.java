package nakup.cart.service.event;

import nakup.cart.dto.OrderFormResponse;
import nakup.cart.entity.event.OrderFormedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisher {
    private static final String TOPIC = "order-formed";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderCreatedEvent(OrderFormedEvent order) {
        kafkaTemplate.send(TOPIC, "order-created", order);
    }
}
