package uk.co.n3tw0rk.droidcart.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@JsonIgnoreProperties
@Data
@Document(collection = "product_instances")
public class ProductInstance {
    @Id
    private Integer id;
    @Indexed
    private Integer productId;
    private int quantity;
    private List<Integer> dimensions;
}
