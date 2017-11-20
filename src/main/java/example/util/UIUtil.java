package example.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Philip Mark Gutierrez <pgutierrez@owens.com>
 * @version 1.0
 * @since November 19, 2017
 */
public class UIUtil {
    public static void loadFXmlFile(Node parentNode, FXMLPage fxmlPage) throws IOException {
        Stage stage = (Stage) parentNode.getScene().getWindow();

        Parent parent = FXMLLoader.load(UIUtil.class.getClassLoader().getResource("example/view/" + fxmlPage.getFilename()));
        stage.setScene(new Scene(parent));
    }

    public static void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }
}
