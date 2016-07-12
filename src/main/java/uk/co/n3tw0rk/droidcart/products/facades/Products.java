package uk.co.n3tw0rk.droidcart.products.facades;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.products.domain.Product;
import uk.co.n3tw0rk.droidcart.products.exceptions.ProductDoesNotExistException;
import uk.co.n3tw0rk.droidcart.products.usecase.ProductUseCase;
import uk.co.n3tw0rk.droidcart.utils.rs.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/products")
@Scope("request")
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
    public Response post(Product.Editor product) {
        try {
            return Response.created(productUseCase.insertResource(product)).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{productId}")
    public Response get(@PathParam("productId") Integer productId) {
        try {
            return Response.ok(productUseCase.findById(productId)).build();
        } catch (ProductDoesNotExistException productNotExist) {
            return Response.status(404).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{productId}")
    public Response delete(@PathParam("productId") Integer productId) {
        try {
            productUseCase.deleteById(productId);
            return Response.noContent().build();
        } catch (ProductDoesNotExistException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("productId") Integer productId,
                        Product product) {
        try {
            productUseCase.put(productId, product);
            return Response.noContent().build();
        } catch (ProductDoesNotExistException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @PATCH
    @Path("/{productId}")
    public Response patch(@PathParam("productId") Integer productId,
                          Product.Editor product) {
        try {
            productUseCase.patch(productId, product);
            return Response.noContent().build();
        } catch (ProductDoesNotExistException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

}
