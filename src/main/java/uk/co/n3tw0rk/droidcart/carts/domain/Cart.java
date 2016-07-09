package uk.co.n3tw0rk.droidcart.carts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.co.n3tw0rk.droidcart.products.domain.ProductList;

@Data
@Document(collection = "carts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cart extends ProductList {
    @Id
    private Integer id;
    @Indexed
    protected Integer userId;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Create extends Cart {
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Update extends Cart {
    }
}
