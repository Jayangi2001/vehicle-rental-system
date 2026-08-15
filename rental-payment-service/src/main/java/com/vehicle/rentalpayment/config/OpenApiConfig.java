package com.vehicle.rentalpayment.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
    info = @Info(
        title = "Rental & Payment Service API",
        version = "1.0",
        description = "Vehicle Rental System - Rental and Payment management endpoints"
    ),
    security = @SecurityRequirement(name = "X-API-KEY")
)
@SecurityScheme(
    name = "X-API-KEY",
    type = SecuritySchemeType.APIKEY,
    in = SecuritySchemeIn.HEADER,
    paramName = "X-API-KEY"
)
public class OpenApiConfig {
}