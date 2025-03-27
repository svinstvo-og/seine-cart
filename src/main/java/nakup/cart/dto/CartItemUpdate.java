package nakup.cart.dto;

import lombok.*;
import nakup.cart.entity.Cart;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CartItemUpdate {
    private Cart cart;
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
}
