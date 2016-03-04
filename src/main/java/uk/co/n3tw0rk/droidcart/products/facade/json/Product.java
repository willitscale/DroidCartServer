package uk.co.n3tw0rk.droidcart.products.facade.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.n3tw0rk.droidcart.products.domain.DroidDocument;
import uk.co.n3tw0rk.droidcart.products.repository.DataRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by M00SEMARKTWO on 22/02/2016.
 */
@Component
@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
public class Product {

    private final String collection = "products";

    @Autowired
    DataRepository dataRepository;

    @GET
    public Response index() {
        List<DroidDocument> products = dataRepository.find(collection, new uk.co.n3tw0rk.droidcart.products.domain.Product("", "", 0.0d));

        return Response.ok(products).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response post(
            @FormParam("name") String name,
            @FormParam("description") String description,
            @FormParam("price") double price,
            @FormParam("dimensions") List<String> dimensions) {

        uk.co.n3tw0rk.droidcart.products.domain.Product product = new uk.co.n3tw0rk.droidcart.products.domain.Product(
            name,
            description,
            price
        );

        dataRepository.insertOne("products", product);

        return Response.ok(product).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) {
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response put(@PathParam("id") String id) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        return Response.ok().build();
    }
}
