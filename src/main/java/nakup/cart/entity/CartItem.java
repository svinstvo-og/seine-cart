package nakup.cart.entity;

import jakarta.persistence.*;
import jdk.jfr.Timespan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "cart-item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Cart cart;

    private Long productId;
    private Integer quantity;
    private Double unitPrice;

    @jdk.jfr.Timestamp
    private Timestamp createdAt;
    @jdk.jfr.Timestamp
    private Timestamp updatedAt;
}
