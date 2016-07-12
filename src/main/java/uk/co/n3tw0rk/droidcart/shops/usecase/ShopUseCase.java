package uk.co.n3tw0rk.droidcart.shops.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.shops.domain.Shop;
import uk.co.n3tw0rk.droidcart.shops.exceptions.ShopDoesNotExistException;
import uk.co.n3tw0rk.droidcart.shops.repository.MongoShopRepository;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class ShopUseCase {

    private final MongoShopRepository mongoShopRepository;

    @Autowired
    public ShopUseCase(MongoShopRepository mongoShopRepository) {
        this.mongoShopRepository = mongoShopRepository;
    }

    public List<Shop> findAll(int limit, int offset) {
        return this.mongoShopRepository.findAll(limit, offset);
    }

    public URI insertResource(Shop shop) throws URISyntaxException {
        return new URI(insert(shop).toString());
    }

    public Integer insert(Shop shop) {
        shop.setId(mongoShopRepository.nextId().getSequence());
        mongoShopRepository.insert(shop);
        return shop.getId();
    }

    public void deleteById(Integer shopId) throws ShopDoesNotExistException {
        mongoShopRepository.deleteById(shopId);
    }

    public Shop findById(Integer shopId) throws ShopDoesNotExistException {
        return mongoShopRepository.findById(shopId);
    }

    public void patch(Integer shopId, Shop shop)
            throws IllegalAccessException, ShopDoesNotExistException, InvocationTargetException {
        mongoShopRepository.update(shopId, shop);
    }

    public void put(Integer shopId, Shop shop) throws ShopDoesNotExistException {
        shop.setId(shopId);
        mongoShopRepository.save(shop);
    }
}
