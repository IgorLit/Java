package main.Validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("CardValidator")
public class CardValidator  implements Validator {
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String componentValue = value.toString();
        pattern = Pattern.compile("^[0-9]{6,8}+$");
        matcher = pattern.matcher(componentValue);
        if (!matcher.find()) {
            String message = MessageFormat.format("{0} incorrect format ", componentValue);
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Name validation failed.", "Incorrect format");
            throw new ValidatorException(facesMessage);
        }
    }
}
