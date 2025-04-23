package com.example.apigateway.restclient;

import com.example.apigateway.dto.DocumentDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@Path("/documents")
@RegisterRestClient(baseUri = "stork://document-service")
public interface DocumentClient {

    @POST
    Response createDocument(@HeaderParam("X-Tenant-Id") String tenantId,DocumentDTO document);

    @GET
    @Path("/{id}")
    Response getDocument(@PathParam("id") UUID id,@HeaderParam("X-Tenant-Id") String tenantId);
}
