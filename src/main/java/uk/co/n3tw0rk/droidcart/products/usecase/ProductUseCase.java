package uk.co.n3tw0rk.droidcart.products.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.products.domain.Sequence;
import uk.co.n3tw0rk.droidcart.products.exceptions.ProductNotExist;
import uk.co.n3tw0rk.droidcart.products.repository.MongoProductRepository;

import java.util.List;
import java.util.UUID;

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

    public Integer insert(Product product) {
        product.setId(nextId().getSequence());
        mongoProductRepository.insert(product);
        return product.getId();
    }

    public void deleteById(Integer id) throws ProductNotExist {
        mongoProductRepository.deleteById(id);
    }

    public Product findById(Integer id) throws ProductNotExist {
        return mongoProductRepository.findById(id);
    }

    public Sequence nextId() {
        return mongoProductRepository.nextId();
    }
}
