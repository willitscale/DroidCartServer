package uk.co.n3tw0rk.droidcart.products.facade.html;

import com.sun.jersey.api.view.Viewable;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.products.repository.DataRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by M00SEMARKTWO on 06/02/2016.
 */
@Component
@Path("/product")
public class ProductResource {

    @Autowired
    DataRepository dataRepository;

    @GET
    @Produces("text/html")
    public Response test() {

        StringBuilder data = new StringBuilder();

        if (null != dataRepository) {
            List<Document> documents = dataRepository.find("products", new Product(123, "test", "Test", "test", 10.2d));

            for(Document document:documents) {
                data.append(document.toString());
            }

        } else {
            data.append("dataRepository is null");
        }

        Map<String, Object> map = new HashMap<String,Object>();

        map.put("outcome", data);

        return Response.ok(new Viewable("/product", map)).build();
    }
}
