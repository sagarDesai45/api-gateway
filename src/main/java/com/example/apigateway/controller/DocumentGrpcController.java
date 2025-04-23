package com.example.apigateway.controller;

import com.example.apigateway.config.GrpcClientInterceptor;
import com.example.apigateway.config.TenantIdInterceptor;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.grpc.GrpcService;
import io.quarkus.grpc.RegisterClientInterceptor;
import io.quarkus.grpc.RegisterInterceptor;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import org.acme.grpc.DocumentProcessor;
import org.acme.grpc.DocumentRequest;
import org.acme.grpc.DocumentResponse;

@GrpcService
@RegisterInterceptor(TenantIdInterceptor.class)
public class DocumentGrpcController implements DocumentProcessor {

    @RegisterClientInterceptor(GrpcClientInterceptor.class)
    @GrpcClient("document-service")
    DocumentProcessor documentProcessor;


    @Override
    @RolesAllowed({"admin","viewer"})
    public Uni<DocumentResponse> process(DocumentRequest request) {
                return documentProcessor.process(request);

    }
}
