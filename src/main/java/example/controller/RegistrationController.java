package example.controller;

import de.felixroske.jfxsupport.FXMLController;
import example.entity.Attendee;
import example.service.RegistrationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Philip Mark Gutierrez <pgutierrez@owens.com>
 * @version 1.0
 * @since November 19, 2017
 */
@FXMLController
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField organizationField;
    @FXML
    private TextField emailAddressField;
    @FXML
    private TextField phoneNumberField;

    public void register(ActionEvent event) {
        Attendee attendee = new Attendee();
        attendee.setFirstName(firstNameField.getText());
        attendee.setLastName(lastNameField.getText());
        attendee.setOrganization(organizationField.getText());
        attendee.setEmail(emailAddressField.getText());
        attendee.setPhoneNumber(phoneNumberField.getText());

        registrationService.save(attendee);
    }
}
