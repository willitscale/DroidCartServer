package uk.co.n3tw0rk.droidcart.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Field;
import java.util.List;

@Data
@Document(collection = "products")
@JsonIgnoreProperties(ignoreUnknown = true)
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
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Create extends Product {}

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Update extends Product {}
}
