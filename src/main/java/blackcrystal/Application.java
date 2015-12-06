package blackcrystal;

import blackcrystal.app.PropertyValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;

@SpringBootApplication
public class Application {

    @Bean
    public Validator configurationPropertiesValidator() {
        return new PropertyValidator();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
