package uk.co.n3tw0rk.droidcart.products.repository;

import com.google.common.collect.Lists;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.products.domain.ProductInstance;
import uk.co.n3tw0rk.droidcart.products.exceptions.ProductInstanceDoesNotExistException;
import uk.co.n3tw0rk.droidcart.support.repository.MongoSupportRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Component
public class MongoProductInstanceRepository extends MongoSupportRepository {

    private final static String COLLECTION = "product_instances";

    @Autowired
    public MongoProductInstanceRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate, COLLECTION);
    }

    public ProductInstance findById(Integer id) {
        return mongoTemplate.findById(id, ProductInstance.class);
    }

    public List<ProductInstance> findByIds(List<Integer> ids) {
        if (null == ids) {
            return Lists.newArrayList();
        }

        return mongoTemplate.find(
                new Query(new Criteria().where("_id").in(ids)),
                ProductInstance.class
        );
    }

    public void insert(ProductInstance productInstance) {
        mongoTemplate.insert(productInstance);
    }

    public void save(ProductInstance productInstance)
            throws ProductInstanceDoesNotExistException {
        if (!exists(productInstance.getId(), ProductInstance.class)) {
            throw new ProductInstanceDoesNotExistException();
        }
    }

    public void update(Integer id, ProductInstance productInstance)
            throws InvocationTargetException, IllegalAccessException, ProductInstanceDoesNotExistException {
        Query query = new Query(new Criteria().where("_id").is(id));
        Update update = buildUpdate(productInstance, ProductInstance.class);
        WriteResult writeResult = mongoTemplate.updateFirst(
                query,
                update,
                ProductInstance.class
        );

        if (0 >= writeResult.getN()) {
            throw new ProductInstanceDoesNotExistException();
        }
    }

    public void deleteById(Integer id)
            throws ProductInstanceDoesNotExistException {
        List<ProductInstance> productInstances = mongoTemplate.findAllAndRemove(
                new Query(new Criteria().where("_id").is(id)),
                ProductInstance.class
        );

        if (0 == productInstances.size()) {
            throw new ProductInstanceDoesNotExistException();
        }
    }

}
