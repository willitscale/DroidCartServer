package uk.co.n3tw0rk.droidcart.products.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.products.domain.Product;

import java.util.List;

@Component
public class MongoProductRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoProductRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Product findById(Integer id) {
        return mongoTemplate.findById(id, Product.class);
    }

    public void insert(Product product) {
        mongoTemplate.insert(product);
    }

    public void deleteById(Integer id) {
        mongoTemplate.remove(
                new Query(
                        new Criteria().where("_id").is(id)
                )
        );
    }

    public List<Product> findAll(int limit, int offset) {
        return mongoTemplate.find(
                new Query().limit(limit).skip(offset),
                Product.class
        );
    }
}
