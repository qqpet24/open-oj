package com.xmu.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Configuration
public class CorsConfig extends WebMvcConfigurationSupport {

    static final String[] ORIGINS = new String[]{
            "GET", "POST", "PUT", "DELETE"
    };

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                //.allowCredentials(true)
                .allowedMethods(ORIGINS)
                .maxAge(3600);
        super.addCorsMappings(registry);
    }
}
