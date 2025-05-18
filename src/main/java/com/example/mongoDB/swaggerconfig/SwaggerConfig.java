package com.example.mongoDB.swaggerconfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPISwaggerDoc(){
        return new OpenAPI().info(
                new Info()
                        .title("Springboot project with MongoDb Database")
                        .description("this fully optimisized code...")
                        .version("0.0.1")
        );
    }
}
