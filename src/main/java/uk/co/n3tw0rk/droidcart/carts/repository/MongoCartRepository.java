package uk.co.n3tw0rk.droidcart.carts.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.carts.domain.Cart;
import uk.co.n3tw0rk.droidcart.carts.domain.exceptions.CartDoesNotExistException;
import uk.co.n3tw0rk.droidcart.support.repository.MongoSupportRepository;

import java.util.List;

@Component
public class MongoCartRepository extends MongoSupportRepository {

    public final static String COLLECTION = "carts";

    @Autowired
    public MongoCartRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate, COLLECTION);
    }

    /**
     * Retreive a cart by its ID
     *
     * @param id of the cart
     * @return instance of the cart
     * @throws CartDoesNotExistException
     */
    public Cart findById(Integer id) throws CartDoesNotExistException {
        Cart cart = mongoTemplate.findById(id, Cart.class);

        if (null == cart) {
            throw new CartDoesNotExistException();
        }

        return cart;
    }

    /**
     * Save a cart
     *
     * @param cart to be inserted
     */
    public void save(Cart cart) {
        mongoTemplate.insert(cart);
    }

    /**
     * Returns a list of all carts filtered by the limit and offset
     *
     * @param limit  of non-exclusive carts to return
     * @param offset to start the list from
     * @return a limited list of carts defined by the limit/offset
     */
    public List<Cart> findAll(int limit, int offset) {
        return mongoTemplate.find(
                new Query().limit(limit).skip(offset),
                Cart.class
        );
    }

    /**
     * Delete a cart by its ID
     *
     * @param id of the cart to be deleted
     * @throws CartDoesNotExistException
     */
    public void deleteById(Integer id) throws CartDoesNotExistException {
        List<Cart> carts = mongoTemplate.findAllAndRemove(
                new Query(new Criteria().where("_id").is(id)),
                Cart.class
        );

        if (0 == carts.size()) {
            throw new CartDoesNotExistException();
        }
    }
}
