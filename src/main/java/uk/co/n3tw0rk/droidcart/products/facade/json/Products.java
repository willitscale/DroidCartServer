package uk.co.n3tw0rk.droidcart.products.facade.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.products.repository.DataRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by M00SEMARKTWO on 22/02/2016.
 */
@Component
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class Products {

    @Autowired
    DataRepository dataRepository;

    @GET
    public Response index() {

        return Response.ok().build();
    }
}
