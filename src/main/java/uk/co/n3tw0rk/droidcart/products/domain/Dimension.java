package uk.co.n3tw0rk.droidcart.products.domain;

import org.bson.Document;

/**
 * Created by M00SEMARKTWO on 14/02/2016.
 */
public class Dimension extends Document {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
