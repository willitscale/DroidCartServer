package uk.co.n3tw0rk.droidcart.carts.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.carts.usecase.CartProductsUseCase;
import uk.co.n3tw0rk.droidcart.utils.rs.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/carts/{cartId}")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class CartProducts {

    @PathParam("cartId")
    private Integer cartId;

    private final CartProductsUseCase cartProductsUseCase;

    @Autowired
    public CartProducts(CartProductsUseCase cartProductsUseCase) {
        this.cartProductsUseCase = cartProductsUseCase;
    }

    @GET
    @Path("/products")
    public Response index() {
        return Response.ok().build();
    }

    @GET
    @Path("/products/{productId}")
    public Response get(@PathParam("productId") Integer productId) {
        return Response.ok().build();
    }

    @POST
    public Response create() {
        return Response.ok().build();
    }

    @DELETE
    @Path("/products/{productId}")
    public Response delete(@PathParam("productId") Integer productId) {
        return Response.ok().build();
    }

    @PATCH
    @Path("/products/{productId}")
    public Response patch(@PathParam("productId") Integer productId) {
        return Response.ok().build();
    }

    @PUT
    @Path("/products/{productId}")
    public Response put(@PathParam("productId") Integer productId) {
        return Response.ok().build();
    }



}
