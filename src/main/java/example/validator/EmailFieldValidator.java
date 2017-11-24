package example.validator;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * @author Philip Mark Gutierrez <pgutierrez@owens.com>
 * @version 1.0
 * @since November 24, 2017
 */
public class EmailFieldValidator extends ValidatorBase {
    public EmailFieldValidator() {
        this("A valid email is required");
    }

    public EmailFieldValidator(String message) {
        super(message);
    }

    @Override
    protected void eval() {
        TextInputControl textField = (TextInputControl) srcControl.get();
        String text = textField.getText();

        if (StringUtils.isNotBlank(text)) {
            EmailValidator emailValidator = EmailValidator.getInstance();

            if (!emailValidator.isValid(text)) {
                hasErrors.set(true);
            } else {
                hasErrors.set(false);
            }
        }
    }
}
