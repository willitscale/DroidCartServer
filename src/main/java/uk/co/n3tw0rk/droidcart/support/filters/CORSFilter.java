package uk.co.n3tw0rk.droidcart.support.filters;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

import javax.ws.rs.core.MultivaluedMap;

public class CORSFilter implements ContainerResponseFilter {
    public ContainerResponse filter(ContainerRequest containerRequest,
                                    ContainerResponse containerResponse) {

        MultivaluedMap<String, Object> headers = containerResponse.getHttpHeaders();
        headers.putSingle("Access-Control-Allow-Origin", "*");
        headers.putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH");
        headers.putSingle("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
        return containerResponse;
    }
}
