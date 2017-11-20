package example.util;

/**
 * @author Philip Mark Gutierrez <pgutierrez@owens.com>
 * @version 1.0
 * @since November 19, 2017
 */
public enum FXMLPage {
    HOME_PAGE("HomeView.fxml"),
    REGISTRATION_PAGE("RegistrationView.fxml");

    private String filename;

    FXMLPage(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
