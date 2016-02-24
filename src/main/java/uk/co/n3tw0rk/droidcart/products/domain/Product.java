package uk.co.n3tw0rk.droidcart.products.domain;

import org.bson.Document;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by M00SEMARKTWO on 14/02/2016.
 */
public class Product extends Document {

    private int id;

    private String name;
    private String description;
    private String image;

    private double price;

    private List<Dimension> dimensions;

    /**
     *
     * @param id
     * @param name
     * @param description
     * @param image
     * @param price
     */
    public Product(int id, String name, String description, String image, double price) {
        this(id, name, description, image, price, new LinkedList<Dimension>());
    }

    public Product(int id, String name, String description, String image, double price, List<Dimension> dimensions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.dimensions = dimensions;
    }

    /**
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return
     */
    public List<Dimension> getDimensions() {
        return dimensions;
    }

    /**
     * @param dimensions
     */
    public void setDimensions(List<Dimension> dimensions) {
        this.dimensions = dimensions;
    }
}
