package com.example.apigateway.config;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GrpcClientInterceptor implements ClientInterceptor {

    @Inject
    TenantContext tenantContext;


    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {

        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                String xTenantIdFromContext = TenantIdInterceptor.TENANT_ID_CTX_KEY.get();
                String validTenant = tenantContext.getValidTenant(xTenantIdFromContext);

                Metadata.Key<String> headerKey = Metadata.Key.of("X-Tenant-Id", Metadata.ASCII_STRING_MARSHALLER);
                headers.put(headerKey, validTenant);
                super.start(responseListener, headers);
            }
        };
    }
}
