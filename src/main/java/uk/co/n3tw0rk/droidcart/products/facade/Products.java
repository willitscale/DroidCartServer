package uk.co.n3tw0rk.droidcart.products.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.products.usecase.ProductUseCase;
import uk.co.n3tw0rk.droidcart.utils.rs.PATCH;

import javax.ws.rs.*;
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
    ProductUseCase productUseCase;

    @GET
    public Response index(@DefaultValue("10") @QueryParam("limit") int limit,
                          @DefaultValue("0") @QueryParam("offset") int offset) {
        try {
            return Response.ok(productUseCase.findAll(limit, offset)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Product product) {
        try {
            productUseCase.insert(product);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Integer id) {
        try {
            return Response.ok(productUseCase.findById(id)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        try {
            productUseCase.deleteById(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("id") Integer id,
                        Product product) {
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    public Response patch(@PathParam("id") Integer id,
                          String key,
                          Object value) {
        return Response.ok().build();
    }

}
