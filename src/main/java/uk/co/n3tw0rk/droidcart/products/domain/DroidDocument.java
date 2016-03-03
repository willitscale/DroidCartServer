package uk.co.n3tw0rk.droidcart.products.domain;

import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Created by gateway on 02/03/16.
 */
public abstract class DroidDocument extends Document {

    protected final ObjectId objectId;

    /**
     *
     */
    public DroidDocument() {
        objectId = new ObjectId();
        this.put("_id", objectId);
    }

    /**
     * @return
     */
    public ObjectId getObjectId() {
        return objectId;
    }
}
