package uk.co.n3tw0rk.droidcart.products.usecase;

import com.sun.research.ws.wadl.Doc;
import org.bson.Document;

import java.util.List;

/**
 * Created by M00SEMARKTWO on 20/02/2016.
 */
public interface DataSource {

    /**
     *
     * @param collection
     * @return
     */
    boolean setCollection(String collection);

    /**
     * @param collection
     * @param document
     * @return
     */
    List<Document> find(String collection, Document document);

    /**
     *
     * @param document
     * @return
     */
    List<Document> find(Document document);

    /**
     *
     * @param collection
     * @return
     */
    boolean drop(String collection);

    /**
     *
     * @return
     */
    boolean drop();

    /**
     *
     * @param collection
     * @param document
     * @return
     */
    boolean insertOne(String collection, Document document);

    /**
     *
     * @param document
     * @return
     */
    boolean insertOne(Document document);

    /**
     *
     * @param collection
     * @param document
     * @return
     */
    boolean insert(String collection, List<Document> document);

    /**
     *
     * @param document
     * @return
     */
    boolean insert(List<Document> document);
}
