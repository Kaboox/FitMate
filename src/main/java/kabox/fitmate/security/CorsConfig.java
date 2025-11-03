package kabox.fitmate.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    
    private String vercelFrontendUrl = "https://fit-mate-phi.vercel.app";
    
    private String oldVercelFrontendUrl = "https://fit-mate-8f8bcslhr-kabooxs-projects.vercel.app";
    private String localFrontendUrl = "http://localhost:5173";

   @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        
                       
                        .allowedOrigins(
                                localFrontendUrl,
                                vercelFrontendUrl,
                                oldVercelFrontendUrl
                        ) 
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
