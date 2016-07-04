package uk.co.n3tw0rk.droidcart.support.repository;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import uk.co.n3tw0rk.droidcart.products.domain.Sequence;

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
}
