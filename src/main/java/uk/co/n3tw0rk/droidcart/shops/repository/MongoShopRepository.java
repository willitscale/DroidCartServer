package uk.co.n3tw0rk.droidcart.shops.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.shops.domain.Shop;
import uk.co.n3tw0rk.droidcart.shops.exceptions.ShopDoesNotExistException;
import uk.co.n3tw0rk.droidcart.support.repository.MongoSupportRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Component
public class MongoShopRepository extends MongoSupportRepository {

    public final static String COLLECTION = "shops";

    @Autowired
    public MongoShopRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate, COLLECTION);
    }

    public List<Shop> findAll(int limit, int offset) {
        return mongoTemplate.find(
                new Query().limit(limit).skip(offset),
                Shop.class
        );
    }

    public Shop findById(Integer shopId) throws ShopDoesNotExistException {
        Shop shop = mongoTemplate.findById(shopId, Shop.class);

        if (null == shop) {
            throw new ShopDoesNotExistException();
        }

        return shop;
    }

    public void insert(Shop shop) {
        mongoTemplate.insert(shop);
    }

    public void save(Shop shop) throws ShopDoesNotExistException {
        if (!exists(shop.getId(), Shop.class)) {
            throw new ShopDoesNotExistException();
        }

        mongoTemplate.save(shop);
    }

    public void update(Integer shopId, Shop shop)
            throws InvocationTargetException, IllegalAccessException, ShopDoesNotExistException {
        if (!update(shopId, shop, Shop.class)) {
            throw new ShopDoesNotExistException();
        }
    }

    public void deleteById(Integer shopId) throws ShopDoesNotExistException {
        List<Shop> shops = mongoTemplate.findAllAndRemove(
                new Query(new Criteria().where("_id").is(shopId)),
                Shop.class
        );

        if (0 == shops.size()) {
            throw new ShopDoesNotExistException();
        }
    }

}
