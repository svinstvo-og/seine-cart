package seine.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import seine.cart.entity.Cart;
import seine.cart.repository.CartRepository;

@RestController("/api/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @PostMapping()
    public void addProduct(@RequestBody Cart cart) {
        cartRepository.save(cart);
    }
}
