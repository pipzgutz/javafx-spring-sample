package example;

import de.felixroske.jfxsupport.FXMLController;
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
public class HomeController {
    @Autowired
    private HomeService homeService;
    @FXML
    private TextField nameTextField;

    public void greet(ActionEvent event) {
        System.out.println(homeService.greet(nameTextField.getText()));
    }
}
