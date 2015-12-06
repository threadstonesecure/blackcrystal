package blackcrystal.app;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;


public class PropertyValidator  implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return type == ApplicationProperties.class;
    }

    @Override
    public void validate(Object o, Errors errors)  {
        ValidationUtils.rejectIfEmpty(errors, "workspace", "workspace.empty");
        ApplicationProperties properties = (ApplicationProperties) o;
        if (properties.getWorkspace() != null && !Files.exists(Paths.get(properties.getWorkspace()))) {
            try
            {
                Files.createDirectory(Paths.get(properties.getWorkspace()));
            }catch (IOException e){
                errors.rejectValue("workspace", "Can not create workspace directory, check permissions!");
            }

        }
    }

}
