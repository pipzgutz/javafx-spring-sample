package example.controller;

import de.felixroske.jfxsupport.FXMLController;
import example.service.RegistrationService;
import example.util.FXMLPage;
import example.util.UIUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

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
    private Button backButton;

    public void goToHomePage(ActionEvent event) throws IOException {
        UIUtil.loadFXmlFile(backButton.getParent(), FXMLPage.HOME_PAGE);
    }
}
