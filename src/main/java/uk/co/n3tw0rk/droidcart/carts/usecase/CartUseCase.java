package uk.co.n3tw0rk.droidcart.carts.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.carts.domain.Cart;
import uk.co.n3tw0rk.droidcart.carts.domain.exceptions.CartDoesNotExistException;
import uk.co.n3tw0rk.droidcart.carts.repository.MongoCartRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class CartUseCase {

    private final MongoCartRepository mongoCartRepository;

    @Autowired
    public CartUseCase(MongoCartRepository mongoCartRepository) {
        this.mongoCartRepository = mongoCartRepository;
    }

    public List<Cart> findAll(int limit, int offset) {
        return mongoCartRepository.findAll(limit, offset);
    }

    public Cart findById(Integer cartId) throws CartDoesNotExistException {
        return mongoCartRepository.findById(cartId);
    }

    public URI insertResource(Cart cart) throws URISyntaxException {
        return new URI(insert(cart).toString());
    }

    public Integer insert(Cart cart) {
        cart.setId(mongoCartRepository.nextId().getSequence());
        mongoCartRepository.save(cart);
        return cart.getId();
    }

    public void deleteById(Integer id) throws CartDoesNotExistException {
        mongoCartRepository.deleteById(id);
    }
}
