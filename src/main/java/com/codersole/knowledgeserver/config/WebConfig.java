package com.codersole.knowledgeserver.config;

import com.codersole.knowledgeserver.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.rmi.registry.Registry;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;
    private final CorsProperties corsProperties;

    public WebConfig(AuthInterceptor authInterceptor, CorsProperties corsProperties) {
        this.authInterceptor = authInterceptor;
        this.corsProperties = corsProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns("/auth/login",
            "/users/register",
            // Swagger / OpenAPI

            "/swagger-ui/**",

            "/swagger-ui.html", "/actuator/health",

            "/v3/api-docs/**", "/uploads/**");
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/");
    }

    public void addCorsMappings(CorsRegistry registry) {
        if (corsProperties.getAllowedOrigins().isEmpty()) {

            return;

        }
        registry.addMapping("/**").allowedOrigins(corsProperties.getAllowedOrigins().toArray(new String[0])
        ).allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS").allowedHeaders("*").allowCredentials(true);
    }
}
