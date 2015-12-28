package blackcrystal;

import blackcrystal.app.PropertyValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableScheduling
public class Application {

    /**
     * Validate application configuration.
     *
     * @return
     */
    @Bean
    public Validator configurationPropertiesValidator() {
        return new PropertyValidator();
    }


    @Bean
    public WebMvcConfigurer configureCors() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("PUT", "DELETE","GET","POST");
            }
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
