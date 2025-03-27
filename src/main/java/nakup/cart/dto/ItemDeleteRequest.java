package nakup.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ItemDeleteRequest {
    @JsonProperty("user-id")
    private Long userId;
    @JsonProperty("item-id")
    private Long itemId;
}
