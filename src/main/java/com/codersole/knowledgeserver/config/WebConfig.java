package com.codersole.knowledgeserver.config;

import com.codersole.knowledgeserver.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;

    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
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
        registry.addMapping("/**").allowedOrigins("http://localhost:5173")
            .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS").allowedHeaders("*").allowCredentials(true);
    }
}
