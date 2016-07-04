package uk.co.n3tw0rk.droidcart.products.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.products.exceptions.ProductDoesNotExistException;
import uk.co.n3tw0rk.droidcart.products.repository.MongoProductRepository;

import java.lang.reflect.Field;
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

    public void deleteById(Integer id) throws ProductDoesNotExistException {
        mongoProductRepository.deleteById(id);
    }

    public Product findById(Integer id) throws ProductDoesNotExistException {
        return mongoProductRepository.findById(id);
    }

    public void patch(Integer id, Product product) throws ProductDoesNotExistException,
            IllegalAccessException, InvocationTargetException {
        mongoProductRepository.update(id, product);
    }

    public void put(Integer id, Product product) {
        product.setId(id);
        mongoProductRepository.save(product);
    }
}
