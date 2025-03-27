package nakup.cart.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nakup.cart.dto.ProductAddRequest;
import nakup.cart.entity.Cart;
import nakup.cart.entity.CartItem;
import nakup.cart.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nakup.cart.repository.CartRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                item.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                System.out.println("ITEM WAS UPDATED");
                return;
            }
        }

        items.add(cartItem);

        cart.setCartItem(items);
        cartItem.setCart(cart);

        cartRepository.save(cart);
        System.out.println("ITEM WAS ADDED");
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
}
