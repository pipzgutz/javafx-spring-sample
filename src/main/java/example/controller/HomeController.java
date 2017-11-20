package example.controller;

import de.felixroske.jfxsupport.FXMLController;
import example.service.HomeService;
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
public class HomeController {
    @Autowired
    private HomeService homeService;
    @FXML
    private Button registerButton;

    public void goToRegistrationPage(ActionEvent event) throws IOException {
        UIUtil.loadFXmlFile(registerButton.getParent(), FXMLPage.REGISTRATION_PAGE);
    }
}
