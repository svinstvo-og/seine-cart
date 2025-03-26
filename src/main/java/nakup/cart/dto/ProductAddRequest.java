package nakup.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ProductAddRequest {
    @JsonProperty("product-id")
    private Long productId;
    @JsonProperty("user-id")
    private Long userId;
    @JsonProperty("unit-price")
    private Double unitPrice;
    private Integer quantity;
}
