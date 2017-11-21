package example.controller;

import de.felixroske.jfxsupport.FXMLController;
import example.entity.Attendee;
import example.resp.Response;
import example.service.RegistrationService;
import example.util.UIUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

        Response response = registrationService.save(attendee);

        if (response.isSuccessful()) {
            UIUtil.showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "", response.getMessage());
        } else {
            UIUtil.showAlert(Alert.AlertType.ERROR, "Registration Failed", "", response.getMessage());
        }
    }
}
