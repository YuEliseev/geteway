package org.tardi.gateway.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.tardi.gateway.model.OerResponse;

@Path("/api")
@RegisterRestClient(configKey = "open-exchange-rates")
public interface OpenExchangeRatesClient {

    @GET
    @Path("/latest.json")
    OerResponse getLatest(@QueryParam("app_id") String appId,
                          @QueryParam("symbols") String symbols);

    @GET
    @Path("/historical/{date}.json")
    OerResponse getHistorical(@PathParam("date") String date,
                              @QueryParam("app_id") String appId,
                              @QueryParam("symbols") String symbols);
}
