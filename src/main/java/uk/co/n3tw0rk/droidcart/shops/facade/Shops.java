package uk.co.n3tw0rk.droidcart.shops.facade;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.n3tw0rk.droidcart.shops.domain.Shop;
import uk.co.n3tw0rk.droidcart.shops.exceptions.ShopDoesNotExistException;
import uk.co.n3tw0rk.droidcart.shops.usecase.ShopUseCase;

@Log4j2
@RestController
public class Shops {

    private final ShopUseCase shopUseCase;

    @Autowired
    public Shops(ShopUseCase shopUseCase) {
        this.shopUseCase = shopUseCase;
    }

    @GetMapping(path = "/shops", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity index(@RequestParam(value = "limit" ,defaultValue = "10") int limit,
                                @RequestParam(value = "offset", defaultValue = "0") int offset) {
        try {
            return ResponseEntity.ok(shopUseCase.findAll(limit, offset));
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(path = "/shops", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity post(@RequestBody Shop.Editor shop) {
        try {
            return ResponseEntity.created(shopUseCase.insertResource(shop)).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/shops/{shopId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@PathVariable("shopId") Integer shopId) {
        try {
            return ResponseEntity.ok(shopUseCase.findById(shopId));
        } catch (ShopDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/shops/{shopId}")
    public ResponseEntity delete(@PathVariable("shopId") Integer shopId) {
        try {
            shopUseCase.deleteById(shopId);
            return ResponseEntity.noContent().build();
        } catch (ShopDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(path = "/shops/{shopId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity put(@PathVariable("shopId") Integer shopId, @RequestBody Shop shop) {
        try {
            shopUseCase.put(shopId, shop);
            return ResponseEntity.noContent().build();
        } catch (ShopDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping(path = "/shops/{shopId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity patch(@PathVariable("shopId") Integer shopId,
                          Shop.Editor shop) {
        try {
            shopUseCase.patch(shopId, shop);
            return ResponseEntity.noContent().build();
        } catch (ShopDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
