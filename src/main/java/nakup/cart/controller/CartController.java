package nakup.cart.controller;

import nakup.cart.repository.CartItemRepository;
import nakup.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import nakup.cart.dto.ProductAddRequest;
import nakup.cart.entity.Cart;
import nakup.cart.entity.CartItem;
import nakup.cart.repository.CartRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartService cartService;

    @PostMapping("/")
    public void addItem(@RequestBody ProductAddRequest productAddRequest) {

        CartItem cartItem = new CartItem();
        cartItem.setProductId(productAddRequest.getProductId());
        cartItem.setQuantity(productAddRequest.getQuantity());
        cartItem.setUnitPrice(productAddRequest.getUnitPrice());
        cartItem.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        Cart cart = cartService.loadCart(productAddRequest.getUserId());

        cartService.addCartItem(cartItem, cart);
    }
}
