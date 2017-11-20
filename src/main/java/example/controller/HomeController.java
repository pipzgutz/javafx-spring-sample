package example.controller;

import de.felixroske.jfxsupport.FXMLController;
import example.service.HomeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

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
    private TextField nameTextField;

    public void greet(ActionEvent event) {
        String text = nameTextField.getText();

        if (StringUtils.isNotBlank(text)) {
            System.out.println(homeService.greet(text.trim()));
        }
    }
}
