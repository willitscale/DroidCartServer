package uk.co.n3tw0rk.droidcart.shops.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.shops.domain.Shop;

import java.util.List;

@Component
public class MongoShopRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoShopRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Shop> findAll(int limit, int offset) {
        return mongoTemplate.find(
                new Query().limit(limit).skip(offset),
                Shop.class
        );
    }

}
