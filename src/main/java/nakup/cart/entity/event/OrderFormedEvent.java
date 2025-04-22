package nakup.cart.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import nakup.cart.dto.OrderItemResponse;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public record OrderFormedEvent(
        @JsonProperty("user-id")
        Long userId,
        @JsonProperty("created-at")
        Timestamp ceratedAt,
        HashMap<Long, Integer> items) { }

