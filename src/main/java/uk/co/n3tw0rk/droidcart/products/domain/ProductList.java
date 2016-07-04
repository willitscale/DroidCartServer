package uk.co.n3tw0rk.droidcart.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ProductList {
    protected List<ProductInstance> productInstances = Lists.newArrayList();

    public void add(ProductInstance productInstance) {
        productInstances.add(productInstance);
    }
}
