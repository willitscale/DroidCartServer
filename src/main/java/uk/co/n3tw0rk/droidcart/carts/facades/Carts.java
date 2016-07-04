package uk.co.n3tw0rk.droidcart.carts.facades;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.carts.domain.Cart;
import uk.co.n3tw0rk.droidcart.carts.domain.exceptions.CartDoesNotExistException;
import uk.co.n3tw0rk.droidcart.carts.usecase.CartUseCase;
import uk.co.n3tw0rk.droidcart.utils.rs.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Component
@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class Carts {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final CartUseCase cartUseCase;

    @Autowired
    public Carts(CartUseCase cartUseCase) {
        this.cartUseCase = cartUseCase;
    }

    @GET
    public Response index(@DefaultValue("10") @QueryParam("limit") int limit,
                          @DefaultValue("0") @QueryParam("offset") int offset) {
        try {
            return Response.ok(cartUseCase.findAll(limit, offset)).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Integer id) {
        try {
            return Response.ok(cartUseCase.findById(id)).build();
        } catch (CartDoesNotExistException e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Cart.Create cart) {
        try {
            return Response.created(cartUseCase.insertResource(cart)).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        try {
            cartUseCase.deleteById(id);
            return Response.noContent().build();
        } catch (CartDoesNotExistException e) {
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
                        Cart cart) {
        // TODO implement
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response patch(@PathParam("id") Integer id,
                          String key,
                          Object value) {
        // TODO implement
        return Response.ok().build();
    }
}
