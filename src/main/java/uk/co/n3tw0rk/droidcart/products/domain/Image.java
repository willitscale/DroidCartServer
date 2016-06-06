package uk.co.n3tw0rk.droidcart.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonIgnoreProperties
@Data
@Document(collection = "images")
public class Image {
    @Id
    private Integer id;
    @Indexed
    private String name;
    @Indexed
    private String url;
}
