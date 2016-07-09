package uk.co.n3tw0rk.droidcart.shops.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.shops.domain.Shop;
import uk.co.n3tw0rk.droidcart.shops.repository.MongoShopRepository;

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
}
