package uk.co.n3tw0rk.droidcart.support.repository;

import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.support.domain.Sequence;
import uk.co.n3tw0rk.droidcart.utils.common.StringSupport;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Mongo Suppoer Repository abstraction layer
 */
public abstract class MongoSupportRepository {

    protected final MongoTemplate mongoTemplate;
    protected final String collection;

    /**
     * @param mongoTemplate
     * @param collection
     */
    public MongoSupportRepository(MongoTemplate mongoTemplate,
                                  String collection) {
        this.mongoTemplate = mongoTemplate;
        this.collection = collection;
    }

    /**
     * Get the next Id for the default sequence
     *
     * @return the next available sequence
     */
    public Sequence nextId() {
        return nextId(collection);
    }

    /**
     * Get the next Id for a sequence
     *
     * @param sequenceCollection to get the next Id for
     * @return the next available sequence
     */
    protected Sequence nextId(String sequenceCollection) {
        Sequence sequence = mongoTemplate.findAndModify(
                new Query(new Criteria().where("_id").is(sequenceCollection)),
                new Update().inc("sequence", 1),
                new FindAndModifyOptions().returnNew(true),
                Sequence.class
        );

        if (null == sequence) {
            mongoTemplate.insert(sequence = new Sequence(sequenceCollection, 1));
        }

        return sequence;
    }

    /**
     * Build query update from object and type
     *
     * @param object      build the update from the non null values
     * @param entityClass of the object type to build from
     * @param <T>         object type
     * @return query update rules
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    protected <T> Update buildUpdate(Object object, Class<T> entityClass)
            throws InvocationTargetException, IllegalAccessException {
        Update update = new Update();

        for (Method method : entityClass.getMethods()) {
            if (!method.getName().startsWith("get") || method.getName().equals("getClass")) {
                continue;
            }

            Object newValue = method.invoke(object);

            if (null == newValue) {
                continue;
            }

            String newKey = StringSupport.attributeFromAccessor(method.getName());

            try {
                entityClass.getDeclaredField(newKey);
                update.set(
                        newKey,
                        newValue
                );
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return update;
    }

    /**
     *
     * @param id
     * @param data
     * @param entityClass
     * @param <T>
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    protected <T> boolean update(Object id, Object data, Class<T> entityClass)
            throws InvocationTargetException, IllegalAccessException {
        Query query = new Query(new Criteria().where("_id").is(id));
        Update update = buildUpdate(data, entityClass);
        return (0 >= mongoTemplate.updateFirst(
                query,
                update,
                entityClass
        ).getN());
    }

    /**
     *
     * @param id
     * @param entityClass
     * @param <T>
     * @return
     */
    protected <T> boolean exists(Object id, Class<T> entityClass) {
        return mongoTemplate.exists(
                new Query(new Criteria("_id").is(id)),
                entityClass
        );
    }
}
