package uk.co.n3tw0rk.droidcart.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Create extends Product {}
}
