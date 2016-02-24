package uk.co.n3tw0rk.droidcart.products.repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.co.n3tw0rk.droidcart.products.usecase.DataSource;

import java.util.List;

/**
 * Created by M00SEMARKTWO on 19/02/2016.
 */
@Repository
public class DataRepository {

    @Autowired
    private DataSource dataSource;

    /**
     * @param collection
     * @param document
     * @return
     */
    public List<Document> find(String collection, Document document) {
        return dataSource.find(collection, document);
    }

    /**
     *
     * @param collection
     * @return
     */
    public boolean drop(String collection) {
        return dataSource.drop(collection);
    }

    /**
     *
     * @param collection
     * @param document
     * @return
     */
    public boolean insertOne(String collection, Document document) {
        return dataSource.insertOne(collection, document);
    }

    /**
     *
     * @param collection
     * @return
     */
    public boolean setCollection(String collection) {
        return dataSource.setCollection(collection);
    }

}
