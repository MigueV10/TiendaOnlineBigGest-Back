// com.coderhouse.config.StaticResourceConfig
package com.coderhouse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/**")
            // Servir archivos subidos desde el directorio local "uploads" (independiente del hosting)
            .addResourceLocations("file:uploads/")
            .setCachePeriod(3600);
  }
}
