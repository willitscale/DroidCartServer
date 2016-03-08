package uk.co.n3tw0rk.droidcart.products.usecase;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import uk.co.n3tw0rk.droidcart.products.domain.DroidDocument;
import uk.co.n3tw0rk.droidcart.products.providers.DroidCardCodecProvider;

import java.util.*;

/**
 * Created by M00SEMARKTWO on 19/02/2016.
 */
public class MongoDataSource implements DataSource {

    /** */
    private final static Logger logger = Logger.getLogger(MongoDataSource.class);

    /** */
    private final MongoClient client;

    /** */
    private final MongoDatabase db;

    /** */
    private final MongoCollection<Document> sequence;

    private final Map<String, Boolean> sequences = new HashMap<String, Boolean>();

    /** */
    private String collection;

    /** */
    static String DATABASE = "db";

    /** */
    static String SEQUENCE = "sequences";

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

        db = client.getDatabase(DATABASE);

        sequence = db.getCollection(SEQUENCE);
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
    public boolean insertOne(DroidDocument document) {
        return insertOne(collection, document);
    }

    /**
     * @param collection
     * @param document
     * @return
     */
    public boolean insertOne(String collection, DroidDocument document) {
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
    public boolean insert(List<DroidDocument> document) {
        return insert(document);
    }

    /**
     * @param collection
     * @param document
     * @return
     */
    public boolean insert(String collection, List<DroidDocument> document) {
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
    public List<DroidDocument> find(DroidDocument document) {
        return find(collection, document);
    }

    /**
     * @param collection
     * @param document
     * @return
     */
    public List<DroidDocument> find(String collection, DroidDocument document) {

        final List<DroidDocument> documents = new LinkedList<DroidDocument>();

        try {
            FindIterable<DroidDocument> iterable = db.getCollection(collection).find(document, DroidDocument.class);

            iterable.forEach(new Block<DroidDocument>() {
                public void apply(DroidDocument doc) {
                    documents.add(doc);
                }
            });

        } catch (Exception e) {
            logger.error("Find Exception", e);
        }

        return documents;
    }

    /**
     * @param sequence
     * @return
     */
    public int autoIncrement(String sequence) {

        // Make sure our sequence exists
        createSequence(sequence);

        Document search = new Document("_id", sequence);

        Document increment = new Document("seq", 1);
        Document update = new Document("$inc", increment);
        Document result = this.sequence.findOneAndUpdate(search, update);

        logger.debug("Seq: " + result.get("seq").toString());

        return Integer.parseInt(result.get("seq").toString());
    }

    /**
     * @param sequence
     */
    public void createSequence(String sequence) {

        // Don't create the sequence if it already exists
        if (sequenceExists(sequence)) {
            return;
        }

        Document document = new Document();
        document.append("_id", sequence);
        document.append("seq", 1);
        this.sequence.insertOne(document);

        // Cache that we have created the sequence
        sequences.put(sequence, true);
    }

    /**
     * @param sequence
     * @return
     */
    public boolean sequenceExists(String sequence) {

        // Don't query mongo if we already know the sequence exists
        if (sequences.containsKey(sequence)) {
            return true;
        }

        Document document = new Document();
        document.append("_id", sequence);

        FindIterable<Document> results = this.sequence.find(document, Document.class);

        if (results.iterator().hasNext()) {
            sequences.put(sequence, true);
            return true;
        }

        return false;
    }
}
