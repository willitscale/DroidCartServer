package uk.co.n3tw0rk.droidcart.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Document(collection = "products")
public class Product {
    @Indexed
    private String name;
    private String description;
    private String image;
    private Double price;
    private List<Dimension> dimensions;
}
