package com.iconsult.userservice.config;

// https://www.baeldung.com/spring-rest-openapi-documentation

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Customer APIs", version = "1.0", description = """
        The document provides detailed technical details of Zanbeel App Transactions.
        Details in this document include:
        •\tProcess Flow
        •\tAPI Calls
        •\tAPI Fields
        •\tAPI Response Codes
        """))
public class SwaggerConfiguration
{

}
