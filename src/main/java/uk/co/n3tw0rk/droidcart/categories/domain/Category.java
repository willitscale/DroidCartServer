package uk.co.n3tw0rk.droidcart.categories.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.co.n3tw0rk.droidcart.products.domain.Product;

import java.util.List;

@Data
@Document(collection = "categories")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    @Id
    private Integer id;
    @Indexed
    protected String name;
    protected List<Product> products;
}
