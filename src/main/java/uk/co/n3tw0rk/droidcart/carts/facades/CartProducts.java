package uk.co.n3tw0rk.droidcart.carts.facades;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.n3tw0rk.droidcart.carts.usecase.CartProductsUseCase;

@Log4j2
@RestController
public class CartProducts {

    private final CartProductsUseCase cartProductsUseCase;

    @Autowired
    public CartProducts(CartProductsUseCase cartProductsUseCase) {
        this.cartProductsUseCase = cartProductsUseCase;
    }

    @GetMapping(path = "/carts/{cartId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity index(@PathVariable("cartId") Integer cartId) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/carts/{cartId}/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@PathVariable("cartId") Integer cartId,
                              @PathVariable("productId") Integer productId) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/carts/{cartId}/products/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@PathVariable("cartId") Integer cartId) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/carts/{cartId}/products/{productId}")
    public ResponseEntity delete(@PathVariable("cartId") Integer cartId,
                                 @PathVariable("productId") Integer productId) {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/carts/{cartId}/products/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity patch(@PathVariable("cartId") Integer cartId,
                                @PathVariable("productId") Integer productId) {
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/carts/{cartId}/products/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity put(@PathVariable("cartId") Integer cartId,
                              @PathVariable("productId") Integer productId) {
        return ResponseEntity.noContent().build();
    }


}
