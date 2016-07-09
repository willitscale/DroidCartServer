package uk.co.n3tw0rk.droidcart.products.repository;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.products.exceptions.ProductDoesNotExistException;
import uk.co.n3tw0rk.droidcart.support.repository.MongoSupportRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Component
public class MongoProductRepository extends MongoSupportRepository {

    public final static String COLLECTION = "products";

    @Autowired
    public MongoProductRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate, COLLECTION);
    }

    /**
     * Retreive a product by its ID
     *
     * @param id of the product
     * @return instance of the product
     * @throws ProductDoesNotExistException
     */
    public Product findById(Integer id) throws ProductDoesNotExistException {
        Product product = mongoTemplate.findById(id, Product.class);

        if (null == product) {
            throw new ProductDoesNotExistException();
        }

        return product;
    }

    /**
     * Save a product
     *
     * @param product to be inserted
     */
    public void insert(Product product) {
        mongoTemplate.insert(product);
    }

    /**
     * Save or insert a product
     *
     * @param product to be inserted or saved
     * @throws ProductDoesNotExistException
     */
    public void save(Product product) throws ProductDoesNotExistException {
        if (!exists(product.getId(), Product.class)) {
            throw new ProductDoesNotExistException();
        }

        mongoTemplate.save(product);
    }

    /**
     * Update a product
     *
     * @param id      of the product to be updated
     * @param product containing the new attributes
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws ProductDoesNotExistException
     */
    public void update(Integer id, Product product)
            throws InvocationTargetException, IllegalAccessException, ProductDoesNotExistException {
        Query query = new Query(new Criteria().where("_id").is(id));
        Update update = buildUpdate(product, Product.class);
        WriteResult result = mongoTemplate.updateFirst(
                query,
                update,
                Product.class
        );

        if (0 >= result.getN()) {
            throw new ProductDoesNotExistException();
        }
    }

    /**
     * Delete a product by its ID
     *
     * @param id of the product to be deleted
     * @throws ProductDoesNotExistException
     */
    public void deleteById(Integer id) throws ProductDoesNotExistException {
        List<Product> products = mongoTemplate.findAllAndRemove(
                new Query(new Criteria().where("_id").is(id)),
                Product.class
        );

        if (0 == products.size()) {
            throw new ProductDoesNotExistException();
        }
    }

    /**
     * Returns a list of all products filtered by the limit and offset
     *
     * @param limit  of non-exclusive products to return
     * @param offset to start the list from
     * @return a limited list of products defined by the limit/offset
     */
    public List<Product> findAll(int limit, int offset) {
        return mongoTemplate.find(
                new Query().limit(limit).skip(offset),
                Product.class
        );
    }
}
