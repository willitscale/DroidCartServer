package uk.co.n3tw0rk.droidcart.products.domain;

import org.bson.Document;

/**
 * Created by gateway on 02/03/16.
 */
public abstract class DroidDocument extends Document {

    protected int id;

    /**
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * @param objectId
     */
    public void setId(int objectId) {
        this.id = objectId;
    }
}
