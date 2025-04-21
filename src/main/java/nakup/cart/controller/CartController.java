package nakup.cart.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import nakup.cart.dto.ItemDeleteRequest;
import nakup.cart.dto.OrderFormResponse;
import nakup.cart.repository.CartItemRepository;
import nakup.cart.service.CartService;
import nakup.cart.service.event.OrderEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.RestClientTimeoutProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private RestClientTimeoutProperties restClientTimeoutProperties;

    @Autowired
    private OrderEventPublisher orderEventPublisher;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(@RequestBody ProductAddRequest productAddRequest) {

        CartItem cartItem = new CartItem();
        cartItem.setProductId(productAddRequest.getProductId());
        cartItem.setQuantity(productAddRequest.getQuantity());
        cartItem.setUnitPrice(productAddRequest.getUnitPrice());
        cartItem.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        Cart cart = cartService.loadCart(productAddRequest.getUserId());

        cartService.addCartItem(cartItem, cart);
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteItem(@RequestBody ItemDeleteRequest itemDeleteRequest) {
        Cart cart = cartService.loadCart(itemDeleteRequest.getUserId());

        cartService.deleteCartItem(cart, itemDeleteRequest.getItemId());
    }

    @GetMapping("/")
    public List<CartItem> getCart(@RequestBody ItemDeleteRequest itemDeleteRequest) {
        Cart cart = cartService.loadCart(itemDeleteRequest.getUserId());
        List<CartItem> items = cart.getCartItem();

        for (CartItem item : items) {
            item.setCart(null);
        }
        return cart.getCartItem();
    }

    @GetMapping("/form-order")
    public OrderFormResponse formOrder(@RequestBody ItemDeleteRequest itemDeleteRequest) {
        Cart cart = cartService.loadCart(itemDeleteRequest.getUserId());

        OrderFormResponse order = cartService.form(cart);

        orderEventPublisher.publishOrderCreatedEvent(order);

        return order;
    }
}
