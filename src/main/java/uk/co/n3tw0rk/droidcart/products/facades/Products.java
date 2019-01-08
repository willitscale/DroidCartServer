package uk.co.n3tw0rk.droidcart.products.facades;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.products.exceptions.ProductDoesNotExistException;
import uk.co.n3tw0rk.droidcart.products.usecase.ProductUseCase;

@Log4j2
@RestController
public class Products {

    private final ProductUseCase productUseCase;

    @Autowired
    public Products(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity index(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                @RequestParam(value = "offset", defaultValue = "0") int offset) {
        try {
            return ResponseEntity.ok(productUseCase.findAll(limit, offset));
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(path = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity post(@RequestBody Product.Editor product) {
        try {
            return ResponseEntity.created(productUseCase.insertResource(product)).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@PathVariable("productId") Integer productId) {
        try {
            return ResponseEntity.ok(productUseCase.findById(productId));
        } catch (ProductDoesNotExistException productNotExist) {
            return ResponseEntity.status(404).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/products/{productId}")
    public ResponseEntity delete(@PathVariable("productId") Integer productId) {
        try {
            productUseCase.deleteById(productId);
            return ResponseEntity.noContent().build();
        } catch (ProductDoesNotExistException e) {
            return ResponseEntity.status(404).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(path = "/products/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity put(@PathVariable("productId") Integer productId,
                              @RequestBody Product product) {
        try {
            productUseCase.put(productId, product);
            return ResponseEntity.noContent().build();
        } catch (ProductDoesNotExistException e) {
            return ResponseEntity.status(404).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping(path = "/products/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity patch(@PathVariable("productId") Integer productId,
                                @RequestBody Product.Editor product) {
        try {
            productUseCase.patch(productId, product);
            return ResponseEntity.noContent().build();
        } catch (ProductDoesNotExistException e) {
            return ResponseEntity.status(404).build();
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
