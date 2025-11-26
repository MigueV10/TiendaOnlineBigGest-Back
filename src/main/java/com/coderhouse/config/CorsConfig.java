import org.springframework.context.annotation.Bean;


import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins(
                            "http://localhost:5173",
                            "http://localhost:5174",
                            "https://main.d19lyjmonu04ta.amplifyapp.com",
                            "https://tiendaBGest.us-west-2.elasticbeanstalk.com"
                    )
                    .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(false)
                    .maxAge(3600);
        }
    };
}

void main() {
}
