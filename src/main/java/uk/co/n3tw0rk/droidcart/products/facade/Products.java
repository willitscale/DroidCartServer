package uk.co.n3tw0rk.droidcart.products.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.products.exceptions.ProductDoesNotExistException;
import uk.co.n3tw0rk.droidcart.products.usecase.ProductUseCase;
import uk.co.n3tw0rk.droidcart.utils.rs.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Component
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class Products {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ProductUseCase productUseCase;

    @Autowired
    public Products(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @GET
    public Response index(@DefaultValue("10") @QueryParam("limit") int limit,
                          @DefaultValue("0") @QueryParam("offset") int offset) {
        try {
            return Response.ok(productUseCase.findAll(limit, offset)).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Product.Create product) {
        try {
            return Response.created(productUseCase.insertResource(product)).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Integer id) {
        try {
            return Response.ok(productUseCase.findById(id)).build();
        } catch (ProductDoesNotExistException productNotExist) {
            return Response.status(404).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        try {
            productUseCase.deleteById(id);
            return Response.noContent().build();
        } catch (ProductDoesNotExistException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("id") Integer id,
                        Product product) {
        try {
            productUseCase.put(id, product);
            return Response.ok().build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response patch(@PathParam("id") Integer id,
                          Product.Update product) {
        try {
            productUseCase.patch(id, product);
            return Response.ok().build();
        } catch (ProductDoesNotExistException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

}
