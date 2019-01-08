package uk.co.n3tw0rk.droidcart.carts.facades;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.n3tw0rk.droidcart.carts.domain.Cart;
import uk.co.n3tw0rk.droidcart.carts.exceptions.CartDoesNotExistException;
import uk.co.n3tw0rk.droidcart.carts.usecase.CartUseCase;

@Log4j2
@RestController
public class Carts {

    private final CartUseCase cartUseCase;

    @Autowired
    public Carts(CartUseCase cartUseCase) {
        this.cartUseCase = cartUseCase;
    }

    @GetMapping(path = "/carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity index(@RequestParam(value = "limit" ,defaultValue = "10") int limit,
                                @RequestParam(value = "offset", defaultValue = "0") int offset) {
        try {
            return ResponseEntity.ok(cartUseCase.findAll(limit, offset));
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/carts/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@PathVariable("cartId") Integer cartId) {
        try {
            return ResponseEntity.ok(cartUseCase.findById(cartId));
        } catch (CartDoesNotExistException e) {
            return ResponseEntity.status(404).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(path = "/carts", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity post(@RequestBody Cart.Create cart) {
        try {
            return ResponseEntity.created(cartUseCase.insertResource(cart)).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/carts/{cartId}")
    public ResponseEntity delete(@PathVariable("cartId") Integer cartId) {
        try {
            cartUseCase.deleteById(cartId);
            return ResponseEntity.noContent().build();
        } catch (CartDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(path = "/carts/{cartId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity put(@PathVariable("cartId") Integer cartId,
                        Cart cart) {
        try {
            cartUseCase.put(cartId, cart);
            return ResponseEntity.noContent().build();
        } catch (CartDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping(path = "/carts/{cartId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity patch(@PathVariable("cartId") Integer cartId,
                          Cart.Update cart
    ) {
        try {
            cartUseCase.patch(cartId, cart);
            return ResponseEntity.noContent().build();
        } catch (CartDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
