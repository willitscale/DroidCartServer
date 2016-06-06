package uk.co.n3tw0rk.droidcart.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "categories")
@JsonIgnoreProperties
public class Category {
    @Id
    private Integer id;
    @Indexed
    private String name;
}
