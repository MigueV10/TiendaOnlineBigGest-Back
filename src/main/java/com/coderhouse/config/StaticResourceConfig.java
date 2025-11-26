// com.coderhouse.config.StaticResourceConfig
package com.coderhouse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/**")
            .addResourceLocations("file:/var/app/current/uploads/")
            .setCachePeriod(3600);
  }
}