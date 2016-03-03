package uk.co.n3tw0rk.droidcart.products.domain;

import java.util.List;

/**
 * Created by M00SEMARKTWO on 28/02/2016.
 */
public class ProductInstance extends DroidDocument {

    private String productId;

    private int quantity;

    private List<DimensionInstance> dimensions;

}
