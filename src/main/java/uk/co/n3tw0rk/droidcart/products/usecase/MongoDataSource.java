package uk.co.n3tw0rk.droidcart.products.usecase;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import uk.co.n3tw0rk.droidcart.products.providers.DroidCardCodecProvider;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by M00SEMARKTWO on 19/02/2016.
 */
public class MongoDataSource implements DataSource {

    private final static Logger logger = Logger.getLogger(MongoDataSource.class);

    private final MongoClient client;

    private final MongoDatabase db;

    private String collection;

    /**
     *
     */
    public MongoDataSource() {

        CodecProvider codecProvider = new DroidCardCodecProvider();

        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                CodecRegistries.fromProviders(codecProvider),
                MongoClient.getDefaultCodecRegistry());

        MongoClientOptions options = MongoClientOptions.builder()
                .codecRegistry(codecRegistry).build();

        client = new MongoClient(new ServerAddress(), options);

        db = client.getDatabase("db");
    }

    /**
     * @param collection
     * @return
     */
    public boolean setCollection(String collection) {
        try {
            this.collection = collection;
            return true;
        } catch (Exception e) {
            logger.error("Set Collection Exception", e);
        }

        return false;
    }

    /**
     * @param document
     * @return
     */
    public boolean insertOne(Document document) {
        return insertOne(collection, document);
    }

    /**
     * @param collection
     * @param document
     * @return
     */
    public boolean insertOne(String collection, Document document) {
        try {
            db.getCollection(collection).insertOne(document);
            return true;
        } catch (Exception e) {
            logger.error("Insert One Exception", e);
        }

        return false;
    }

    /**
     * @param document
     * @return
     */
    public boolean insert(List<Document> document) {
        return insert(document);
    }

    /**
     * @param collection
     * @param document
     * @return
     */
    public boolean insert(String collection, List<Document> document) {
        try {
            db.getCollection(collection).insertMany(document);
            return true;
        } catch (Exception e) {
            logger.error("Insert Exception", e);
        }

        return false;
    }

    /**
     * @return
     */
    public boolean drop() {
        return drop(collection);
    }

    /**
     * @param collection
     * @return
     */
    public boolean drop(String collection) {

        try {
            db.getCollection(collection).drop();
            return true;
        } catch (Exception e) {
            logger.error("Drop Exception", e);
        }

        return false;
    }

    /**
     * @param document
     * @return
     */
    public List<Document> find(Document document) {
        return find(collection, document);
    }

    /**
     * @param collection
     * @param document
     * @return
     */
    public List<Document> find(String collection, Document document) {

        final List<Document> documents = new LinkedList<Document>();

        try {
            FindIterable<Document> iterable = db.getCollection(collection).find(document);

            iterable.forEach(new Block<Document>() {
                public void apply(Document product) {
                    documents.add(product);
                }
            });

        } catch (Exception e) {
            logger.error("Find Exception", e);
        }

        return documents;
    }
}
