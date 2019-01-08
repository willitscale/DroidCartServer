package uk.co.n3tw0rk.droidcart.carts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.co.n3tw0rk.droidcart.products.domain.ProductInstance;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "carts")
@JsonIgnoreProperties(ignoreUnknown = true)
@CompoundIndexes({
        @CompoundIndex(
                name = "shop_user_idx",
                def = "{'shopId':1,'userId':1}",
                sparse = true,
                background = true
        )
})
public class Cart {
    @Id
    private Integer id;
    @Indexed
    protected Integer userId;
    protected Integer shopId;

    protected List<Integer> productIds;
    protected Date created;

    @Data
    @EqualsAndHashCode(callSuper = false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Create extends Cart {
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Update extends Cart {
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Resource extends Cart {
        protected List<ProductInstance> products;
        public Resource(Cart cart, List<ProductInstance> products) {
            this.products = products;
            this.setId(cart.getId());
            this.setUserId(cart.getUserId());
        }
    }
}
