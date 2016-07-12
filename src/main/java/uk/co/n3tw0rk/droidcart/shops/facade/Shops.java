package uk.co.n3tw0rk.droidcart.shops.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.products.exceptions.ProductDoesNotExistException;
import uk.co.n3tw0rk.droidcart.shops.domain.Shop;
import uk.co.n3tw0rk.droidcart.shops.exceptions.ShopDoesNotExistException;
import uk.co.n3tw0rk.droidcart.shops.usecase.ShopUseCase;
import uk.co.n3tw0rk.droidcart.utils.rs.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;

@Component
@Path("/shops")
@Produces(MediaType.APPLICATION_JSON)
public class Shops {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ShopUseCase shopUseCase;

    @Autowired
    public Shops(ShopUseCase shopUseCase) {
        this.shopUseCase = shopUseCase;
    }

    @GET
    public Response index(@DefaultValue("10") @QueryParam("limit") int limit,
                          @DefaultValue("0") @QueryParam("offset") int offset) {
        try {
            return Response.ok(shopUseCase.findAll(limit, offset)).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Shop.Editor shop) {
        try {
            return Response.created(shopUseCase.insertResource(shop)).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{shopId}")
    public Response get(@PathParam("shopId") Integer shopId) {
        try {
            return Response.ok(shopUseCase.findById(shopId)).build();
        } catch (ShopDoesNotExistException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{shopId}")
    public Response delete(@PathParam("shopId") Integer shopId) {
        try {
            shopUseCase.deleteById(shopId);
            return Response.noContent().build();
        } catch (ShopDoesNotExistException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{shopId}")
    public Response put(@PathParam("shopId") Integer shopId,
                        Shop shop) {
        try {
            shopUseCase.put(shopId, shop);
            return Response.noContent().build();
        } catch (ShopDoesNotExistException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }

    @PATCH
    @Path("/{shopId}")
    public Response patch(@PathParam("shopId") Integer shopId,
                          Shop.Editor shop) {
        try {
            shopUseCase.patch(shopId, shop);
            return Response.noContent().build();
        } catch (ShopDoesNotExistException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            logger.error(e.toString());
            return Response.serverError().build();
        }
    }
}
