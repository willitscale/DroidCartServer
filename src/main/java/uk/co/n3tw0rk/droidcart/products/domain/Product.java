package uk.co.n3tw0rk.droidcart.products.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "products")
public class Product {
    @Id
    private Integer id;
    @Indexed
    protected String name;
    protected String description;
    protected String image;
    protected Double price;
    protected List<Dimension> dimensions;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class Editor extends Product {}
}
