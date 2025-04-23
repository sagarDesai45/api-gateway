package com.example.apigateway.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonArray;
import jakarta.json.JsonString;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class TenantContext {

    @Inject
    JsonWebToken jwt;
    public  String getValidTenant(String xTenantId)
    {
        JsonArray tenantIdArray = jwt.getClaim("tenant_id");
        List<String> tenantIds = tenantIdArray.getValuesAs(JsonString.class)
                .stream()
                .map(JsonString::getString)
                .toList();
        if (tenantIds != null && !tenantIds.isEmpty()) {
            if(tenantIds.contains(xTenantId))
            {
                return xTenantId;
            }
            return tenantIds.get(0);
        }

        return null;
    }

}
