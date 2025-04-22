package nakup.cart.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderItem(@JsonProperty("product-id") Long productId, Integer quantity) {
}
