package nakup.cart.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class OrderFormResponse {
    @JsonProperty("user-id")
    private Long userId;
    @JsonProperty("cerated-at")
    private Timestamp ceratedAt;

    List<OrderItemResponse> items;
}
