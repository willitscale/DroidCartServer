package uk.co.n3tw0rk.droidcart.products.usecase;

import uk.co.n3tw0rk.droidcart.products.domain.DroidDocument;

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
    List<DroidDocument> find(String collection, DroidDocument document);

    /**
     *
     * @param document
     * @return
     */
    List<DroidDocument> find(DroidDocument document);

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
    boolean insertOne(String collection, DroidDocument document);

    /**
     *
     * @param document
     * @return
     */
    boolean insertOne(DroidDocument document);

    /**
     *
     * @param collection
     * @param document
     * @return
     */
    boolean insert(String collection, List<DroidDocument> document);

    /**
     *
     * @param document
     * @return
     */
    boolean insert(List<DroidDocument> document);
}
