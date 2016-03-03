package uk.co.n3tw0rk.droidcart.products.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by M00SEMARKTWO on 26/02/2016.
 */
public class ProductList extends DroidDocument {
    /** */
    protected final Map<String,Product> products = new HashMap<String, Product>();

    /**
     *
     * @param key
     * @param product
     */
    public void addProduct(String key, Product product) {
        products.put(key, product);
    }

    /**
     *
     * @param key
     * @return
     */
    @JsonProperty
    public Product getProduct(String key) {
        return products.get(key);
    }

    /**
     *
     * @return
     */
    @JsonProperty
    public Map<String,Product> getProducts() {
        return this.products;
    }

}
