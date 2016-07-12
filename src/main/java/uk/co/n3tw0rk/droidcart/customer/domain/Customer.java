package uk.co.n3tw0rk.droidcart.customer.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
@Data
public class Customer {
    private String email;
    private String forename;
    private String surname;
    private String postcode;
    private Country country;
}
