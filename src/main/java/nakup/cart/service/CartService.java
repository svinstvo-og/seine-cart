package nakup.cart.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nakup.cart.dto.OrderFormResponse;
import nakup.cart.dto.OrderItemResponse;
import nakup.cart.entity.Cart;
import nakup.cart.entity.CartItem;
import nakup.cart.entity.event.OrderFormedEvent;
import nakup.cart.entity.event.OrderItem;
import nakup.cart.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nakup.cart.repository.CartRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public void addCartItem(CartItem cartItem, Cart cart) {
        List<CartItem> items = cart.getCartItem();

        for (CartItem item : items) {
            if (item.getProductId().equals(cartItem.getProductId())) {
                if (item.getQuantity() + cartItem.getQuantity() <= 0) {
                    items.remove(item);
                    System.out.println("ITEM WAS REMOVED");
                    return;
                }
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                item.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                System.out.println("ITEM WAS UPDATED");
                return;
            }
        }
        if (cartItem.getQuantity() > 0) {
            items.add(cartItem);

            cart.setCartItem(items);
            cartItem.setCart(cart);

            cartRepository.save(cart);
            System.out.println("ITEM WAS ADDED");
            return;
        }
        System.out.println("ITEM WAS NOT ADDED");
    }

    public Cart loadCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setCartItem(new ArrayList<>());
        }
        return cart;
    }

    @Transactional
    public void deleteCartItem(Cart cart, Long itemId) {
        List<CartItem> items = cart.getCartItem();
        for (CartItem item : items) {
            if (item.getCartItemId().equals(itemId)) {
                items.remove(item);
                System.out.println("ITEM WAS REMOVED");
                return;
            }
        }
        System.out.println("ITEM WAS NOT FOUND");
    }

    public OrderFormResponse form(Cart cart) {
        List<OrderItemResponse> orderItems = new ArrayList<>();
        OrderItemResponse orderItemResponse = new OrderItemResponse();
        for (CartItem item : cart.getCartItem()) {
            orderItemResponse.setProductId(item.getProductId());
            orderItemResponse.setQuantity(item.getQuantity());
            orderItems.add(orderItemResponse);
        }

        OrderFormResponse response = new OrderFormResponse();
        response.setCeratedAt(Timestamp.valueOf(LocalDateTime.now()));
        response.setItems(orderItems);
        response.setUserId(cart.getUserId());

        return response;
    }

    public OrderFormedEvent formEvent(Cart cart) {
        OrderFormResponse order = form(cart);
        HashMap<Long, Integer> items = new HashMap<>();
        for (OrderItemResponse item : order.getItems()) {
            items.put(item.getProductId(), item.getQuantity());
        }
        return new OrderFormedEvent(order.getUserId(), order.getCeratedAt(), items);
    }
}
