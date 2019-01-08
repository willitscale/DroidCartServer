package uk.co.n3tw0rk.droidcart.shops.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.co.n3tw0rk.droidcart.categories.domain.Category;

import java.util.List;

@Document(collection = "shops")
@Data
public class Shop {
    @Id
    private Integer id;
    @Indexed
    protected String name;
    protected List<Category> categories;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class Editor extends Shop {}
}
