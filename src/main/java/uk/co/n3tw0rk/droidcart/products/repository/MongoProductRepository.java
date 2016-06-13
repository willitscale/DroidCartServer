package uk.co.n3tw0rk.droidcart.products.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.products.domain.Sequence;
import uk.co.n3tw0rk.droidcart.products.exceptions.ProductNotExist;

import java.util.List;

@Component
public class MongoProductRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoProductRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Product findById(Integer id) throws ProductNotExist {
        Product product = mongoTemplate.findById(id, Product.class);

        if (null == product) {
            throw new ProductNotExist();
        }

        return product;
    }

    public void insert(Product product) {
        mongoTemplate.insert(product);
    }

    public void deleteById(Integer id) throws ProductNotExist {
        List<Product> products = mongoTemplate.findAllAndRemove(
                new Query(new Criteria().where("_id").is(id)),
                Product.class
        );

        if (0 == products.size()) {
            throw new ProductNotExist();
        }
    }

    public List<Product> findAll(int limit, int offset) {
        return mongoTemplate.find(
                new Query().limit(limit).skip(offset),
                Product.class
        );
    }

    public Sequence nextId() {
        Sequence sequence = mongoTemplate.findAndModify(
                new Query(new Criteria().where("_id").is("products")),
                new Update().inc("sequence", 1),
                new FindAndModifyOptions().returnNew(true),
                Sequence.class
        );

        if (null == sequence) {
            mongoTemplate.insert(sequence = new Sequence("products", 1));
        }

        return sequence;
    }
}
