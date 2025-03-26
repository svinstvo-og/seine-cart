package nakup.cart.controller;

import nakup.cart.repository.CartItemRepository;
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

    @PostMapping("/")
    public void addProduct(@RequestBody ProductAddRequest productAddRequest) {
        CartItem cartItem = new CartItem();

        cartItem.setProductId(productAddRequest.getProductId());
        cartItem.setQuantity(productAddRequest.getQuantity());
        cartItem.setUnitPrice(productAddRequest.getUnitPrice());
        cartItem.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        cartItemRepository.save(cartItem);

        Cart cart = cartRepository.findByUserId(productAddRequest.getUserId());

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(productAddRequest.getUserId());
            cart.setCartItem(new ArrayList<>());
        }

        List<CartItem> items = cart.getCartItem();
        items.add(cartItem);
        cart.setCartItem(items);

        cartRepository.save(cart);
    }
}
