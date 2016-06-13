package uk.co.n3tw0rk.droidcart.products.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequences")
@Data
@AllArgsConstructor
public class Sequence {
    @Id
    private String collection;
    @Indexed
    private Integer sequence;
}
