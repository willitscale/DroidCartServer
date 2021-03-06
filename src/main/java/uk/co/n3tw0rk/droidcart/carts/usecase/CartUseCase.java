package uk.co.n3tw0rk.droidcart.carts.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.carts.domain.Cart;
import uk.co.n3tw0rk.droidcart.carts.exceptions.CartDoesNotExistException;
import uk.co.n3tw0rk.droidcart.carts.repository.MongoCartRepository;
import uk.co.n3tw0rk.droidcart.products.repository.MongoProductInstanceRepository;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class CartUseCase {

    private final MongoCartRepository mongoCartRepository;

    private final MongoProductInstanceRepository mongoProductInstanceRepository;

    @Autowired
    public CartUseCase(MongoCartRepository mongoCartRepository,
                       MongoProductInstanceRepository mongoProductInstanceRepository) {
        this.mongoCartRepository = mongoCartRepository;
        this.mongoProductInstanceRepository = mongoProductInstanceRepository;
    }

    public List<Cart> findAll(int limit, int offset) {
        return mongoCartRepository.findAll(limit, offset);
    }

    public Cart.Resource findById(Integer cartId) throws CartDoesNotExistException {
        Cart cart = mongoCartRepository.findById(cartId);
        return new Cart.Resource(
                cart,
                mongoProductInstanceRepository.findByIds(cart.getProductIds())
        );
    }

    public URI insertResource(Cart cart) throws URISyntaxException {
        return new URI(insert(cart).toString());
    }

    public Integer insert(Cart cart) {
        cart.setId(mongoCartRepository.nextId().getSequence());
        mongoCartRepository.insert(cart);
        return cart.getId();
    }

    public void deleteById(Integer id) throws CartDoesNotExistException {
        mongoCartRepository.deleteById(id);
    }

    public void patch(Integer id, Cart cart) throws CartDoesNotExistException,
            IllegalAccessException, InvocationTargetException {
        mongoCartRepository.update(id, cart);
    }

    public void put(Integer id, Cart cart) throws CartDoesNotExistException {
        cart.setId(id);
        mongoCartRepository.save(cart);
    }
}
