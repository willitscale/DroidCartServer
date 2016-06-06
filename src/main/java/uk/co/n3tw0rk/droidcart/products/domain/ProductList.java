package uk.co.n3tw0rk.droidcart.products.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by M00SEMARKTWO on 26/02/2016.
 */
public class ProductList {

    protected final Map<String,Product> products = new HashMap<String, Product>();


}
