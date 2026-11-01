package org.tardi.gateway.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.tardi.gateway.client.OpenExchangeRatesClient;
import org.tardi.gateway.model.records.CurrencyResponse;
import org.tardi.gateway.model.records.MessageDto;
import org.tardi.gateway.model.OerResponse;
import org.tardi.gateway.service.CurrencyService;
import org.tardi.gateway.service.GetewayService;

@Path("/gateway")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON})
public class CurrencyResource {

    @ConfigProperty(name = "openexchangerates.app.id")
    String oerAppId;

    @Inject
    @RestClient
    OpenExchangeRatesClient client;

    @Inject
    GetewayService getewayService;

    @Inject
    CurrencyService currencyService;

    @GET
    @Path("/rate/yesterday/{symbol}")
    public CurrencyResponse getYesterdayRate(@PathParam("symbol") String symbol) {
        return currencyService.getYesterdayRate(symbol);
    }

    @POST
    @Path("/send")
    public Response send(MessageDto request) {
        MessageDto response = getewayService.send(request);
        return Response.ok(response).build();
    }
}
