package uk.co.n3tw0rk.droidcart.products.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.products.exceptions.ProductDoesNotExistException;
import uk.co.n3tw0rk.droidcart.products.repository.MongoProductRepository;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class ProductUseCase {

    private final MongoProductRepository mongoProductRepository;

    @Autowired
    public ProductUseCase(MongoProductRepository mongoProductRepository) {
        this.mongoProductRepository = mongoProductRepository;
    }

    public List<Product> findAll(int limit, int offset) {
        return mongoProductRepository.findAll(limit, offset);
    }

    public URI insertResource(Product product) throws URISyntaxException {
        return new URI(insert(product).toString());
    }

    public Integer insert(Product product) {
        product.setId(mongoProductRepository.nextId().getSequence());
        mongoProductRepository.insert(product);
        return product.getId();
    }

    public void deleteById(Integer productId) throws ProductDoesNotExistException {
        mongoProductRepository.deleteById(productId);
    }

    public Product findById(Integer productId) throws ProductDoesNotExistException {
        return mongoProductRepository.findById(productId);
    }

    public void patch(Integer productId, Product product)
            throws ProductDoesNotExistException, IllegalAccessException, InvocationTargetException {
        mongoProductRepository.update(productId, product);
    }

    public void put(Integer productId, Product product) throws ProductDoesNotExistException {
        product.setId(productId);
        mongoProductRepository.save(product);
    }
}
