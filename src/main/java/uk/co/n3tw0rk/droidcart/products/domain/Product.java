package uk.co.n3tw0rk.droidcart.products.domain;

import org.springframework.beans.factory.annotation.Autowired;
import uk.co.n3tw0rk.droidcart.products.repository.DataRepository;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by M00SEMARKTWO on 14/02/2016.
 */
public class Product extends DroidDocument {

    private String name;
    private String description;
    private String image;

    private double price;

    private List<Dimension> dimensions;

    public Product() {

    }

    /**
     * @param id
     */
    public Product(int id) {
        this.id = id;
    }

    /**
     *
     * @param name
     * @param description
     * @param price
     */
    public Product(int id, String name, String description, double price) {
        this(id, name, description, "", price);
    }

    /**
     *
     * @param name
     * @param description
     * @param image
     * @param price
     */
    public Product(int id, String name, String description, String image, double price) {
        this(id, name, description, image, price, new LinkedList<Dimension>());
    }

    /**
     *
     * @param name
     * @param description
     * @param image
     * @param price
     * @param dimensions
     */
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
