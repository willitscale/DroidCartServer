package uk.co.n3tw0rk.droidcart.products.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.products.repository.MongoProductRepository;

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

    public void insert(Product product) {
        mongoProductRepository.insert(product);
    }

    public void deleteById(Integer id) {
        mongoProductRepository.deleteById(id);
    }

    public Product findById(Integer id) {
        return mongoProductRepository.findById(id);
    }

}
