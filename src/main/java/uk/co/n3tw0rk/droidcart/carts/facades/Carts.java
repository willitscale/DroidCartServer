package uk.co.n3tw0rk.droidcart.carts.facades;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.carts.domain.Cart;
import uk.co.n3tw0rk.droidcart.carts.exceptions.CartDoesNotExistException;
import uk.co.n3tw0rk.droidcart.carts.usecase.CartUseCase;
import uk.co.n3tw0rk.droidcart.utils.rs.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/carts")
@Scope("request")
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
    @Path("/{cartId}")
    public Response get(@PathParam("cartId") Integer cartId) {
        try {
            return Response.ok(cartUseCase.findById(cartId)).build();
        } catch (CartDoesNotExistException e) {
            return Response.status(404).build();
        } catch (Exception e) {
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
    @Path("/{cartId}")
    public Response delete(@PathParam("cartId") Integer cartId) {
        try {
            cartUseCase.deleteById(cartId);
            return Response.noContent().build();
        } catch (CartDoesNotExistException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{cartId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("cartId") Integer cartId,
                        Cart cart) {
        try {
            cartUseCase.put(cartId, cart);
            return Response.ok().build();
        } catch (CartDoesNotExistException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @PATCH
    @Path("/{cartId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response patch(@PathParam("cartId") Integer cartId,
                          Cart.Update cart
    ) {
        try {
            cartUseCase.patch(cartId, cart);
            return Response.ok().build();
        } catch (CartDoesNotExistException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }
}
