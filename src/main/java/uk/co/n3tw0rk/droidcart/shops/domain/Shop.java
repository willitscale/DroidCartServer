package uk.co.n3tw0rk.droidcart.shops.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.co.n3tw0rk.droidcart.categories.domain.Category;

import java.util.List;

@Data
@Document(collection = "shops")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shop {
    @Id
    private Integer id;
    @Indexed
    protected String name;
    protected List<Category> categories;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Editor extends Shop {}
}
