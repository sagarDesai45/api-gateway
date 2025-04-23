package com.example.apigateway.controller;

import com.example.apigateway.config.TenantContext;
import com.example.apigateway.dto.DocumentDTO;
import com.example.apigateway.restclient.DocumentClient;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@Path("/documents")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Authenticated
public class DocumentController {

    @Inject
    @RestClient
    DocumentClient restClient;

    @Inject
    TenantContext tenantContext;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response createDocument(DocumentDTO document,@HeaderParam("X-Tenant-Id") String xTenantId) {
        String tenantId= tenantContext.getValidTenant(xTenantId);
        return restClient.createDocument(tenantId,document);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin","viewer"})
    public Response getDocument(@PathParam("id") UUID id,@HeaderParam("X-Tenant-Id") String xTenantId) {
        String tenantId= tenantContext.getValidTenant(xTenantId);
        return restClient.getDocument(id,tenantId);
    }
}
