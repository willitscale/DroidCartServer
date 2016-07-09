package uk.co.n3tw0rk.droidcart.shops.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.n3tw0rk.droidcart.shops.usecase.ShopUseCase;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
            } catch(Exception e) {
                logger.error(e.toString());
                return Response.serverError().build();
            }
    }
}
