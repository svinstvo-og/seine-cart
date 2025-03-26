package seine.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seine.cart.dto.ProductAddRequest;
import seine.cart.entity.Cart;
import seine.cart.entity.CartItem;
import seine.cart.repository.CartRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/")
    public void addProduct(@RequestBody ProductAddRequest productAddRequest) {
        System.out.println("NIGGGGGGAAARRR");
        CartItem cartItem = new CartItem();

        cartItem.setProductId(productAddRequest.getProductId());
        cartItem.setQuantity(productAddRequest.getQuantity());
        cartItem.setUnitPrice(productAddRequest.getUnitPrice());

        //System.out.println(cartRepository.findByUserId(productAddRequest.getUserId()));
        Cart cart = cartRepository.findByUserId(productAddRequest.getUserId());

        //Cart cart = new Cart();

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(productAddRequest.getUserId());
            cart.setCartItem(new ArrayList<>());
            cartRepository.save(cart);
        }

        List<CartItem> items = cart.getCartItem();
        items.add(cartItem);
        cart.setCartItem(items);


        try {
            cartRepository.save(cart);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
