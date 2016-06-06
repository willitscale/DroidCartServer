package uk.co.n3tw0rk.droidcart.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonIgnoreProperties
@Data
@Document(collection = "dimension_instances")
public class DimensionInstance {
    @Id
    private int id;
}
