package uk.co.n3tw0rk.droidcart.support.repository;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import uk.co.n3tw0rk.droidcart.support.domain.Sequence;
import uk.co.n3tw0rk.droidcart.utils.common.StringSupport;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class MongoSupportRepository {

    protected final MongoTemplate mongoTemplate;
    protected final String collection;

    public MongoSupportRepository(MongoTemplate mongoTemplate,
                                  String collection) {
        this.mongoTemplate = mongoTemplate;
        this.collection = collection;
    }

    public Sequence nextId() {
        return nextId(collection);
    }

    private Sequence nextId(String sequenceCollection) {
        Sequence sequence = mongoTemplate.findAndModify(
                new Query(Criteria.where("_id").is(sequenceCollection)),
                new Update().inc("sequence", 1),
                new FindAndModifyOptions().returnNew(true),
                Sequence.class
        );

        if (null == sequence) {
            mongoTemplate.insert(sequence = new Sequence(sequenceCollection, 1));
        }

        return sequence;
    }

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

    protected <T> boolean update(Object id, Object data, Class<T> entityClass)
            throws InvocationTargetException, IllegalAccessException {
        new Criteria();
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = buildUpdate(data, entityClass);
        return (0 >= mongoTemplate.updateFirst(
                query,
                update,
                entityClass
        ).getModifiedCount());
    }

    protected <T> boolean exists(Object id, Class<T> entityClass) {
        return mongoTemplate.exists(
                new Query(new Criteria("_id").is(id)),
                entityClass
        );
    }
}
