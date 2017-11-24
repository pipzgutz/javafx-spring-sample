package example.controller;

import de.felixroske.jfxsupport.FXMLController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import example.entity.Attendee;
import example.resp.Response;
import example.service.ExportService;
import example.service.RegistrationService;
import example.util.UIUtil;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * @author Philip Mark Gutierrez <pgutierrez@owens.com>
 * @version 1.0
 * @since November 19, 2017
 */
@FXMLController
public class RegistrationController implements Initializable {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private ExportService exportService;

    @FXML
    private FontAwesomeIconView logo;
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
    @FXML
    private ComboBox lookingFor;
    @FXML
    private ListView<String> phpListView;
    private Map<String, ObservableValue<Boolean>> phpTrainings = new HashMap<>();
    @FXML
    private ListView<String> dotNetListView;
    private Map<String, ObservableValue<Boolean>> dotNetTrainings = new HashMap<>();
    @FXML
    private ListView<String> javaListView;
    private Map<String, ObservableValue<Boolean>> javaTrainings = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTrainings(phpListView, phpTrainings, Arrays.asList("Core", "Falcon"));
        initTrainings(dotNetListView, dotNetTrainings, Arrays.asList("Core", "Full Stack"));
        initTrainings(javaListView, javaTrainings, Arrays.asList("Core", "Web", "Spring", "Full Stack"));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem exportToExcelMenuItem = new MenuItem("Export to Excel");
        exportToExcelMenuItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Registrations");
            String initialFilePath = System.getProperty("user.home") + File.separator + "Desktop";
            fileChooser.setInitialDirectory(new File(initialFilePath));
            fileChooser.setInitialFileName("event-registration.xlsx");

            File file = fileChooser.showSaveDialog(logo.getScene().getWindow());

            if (file != null) {
                exportService.exportToExcel(file);
            }
        });
        contextMenu.getItems().addAll(exportToExcelMenuItem);
        logo.setOnContextMenuRequested(event -> contextMenu.show(logo, event.getScreenX(), event.getScreenY()));
    }

    private void initTrainings(ListView<String> listView, Map<String, ObservableValue<Boolean>> trainings, List<String> contents) {
        contents.forEach(content -> trainings.put(content, new SimpleBooleanProperty(false)));
        listView.getItems().addAll(trainings.keySet());
        listView.setCellFactory(CheckBoxListCell.forListView(trainings::get));
    }

    public void register(ActionEvent event) {
        Attendee attendee = new Attendee();
        attendee.setFirstName(firstNameField.getText());
        attendee.setLastName(lastNameField.getText());
        attendee.setOrganization(organizationField.getText());
        attendee.setEmail(emailAddressField.getText());
        attendee.setPhoneNumber(phoneNumberField.getText());
        attendee.setLookingFor((String) lookingFor.getValue());
        setTrainingsInterestedIn(attendee);

        Response response = registrationService.save(attendee);

        if (response.isSuccessful()) {
            UIUtil.showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "", response.getMessage());
        } else {
            UIUtil.showAlert(Alert.AlertType.ERROR, "Registration Failed", "", response.getMessage());
        }
    }

    private void setTrainingsInterestedIn(Attendee attendee) {
        List<String> trainings = new ArrayList<>();

        addToTrainings(phpTrainings, "PHP", trainings);
        addToTrainings(dotNetTrainings, ".NET", trainings);
        addToTrainings(javaTrainings, "Java", trainings);

        StringJoiner trainingsSJ = new StringJoiner(",");
        trainings.forEach(trainingsSJ::add);

        attendee.setTrainingsInterestedIn(trainingsSJ.toString());
    }

    private void addToTrainings(Map<String, ObservableValue<Boolean>> trainings, String technology, List<String> trainingsInterestedIn) {
        trainings.forEach((training, isTrainingSelected) -> {
            if (isTrainingSelected.getValue()) {
                trainingsInterestedIn.add(technology + "-" + training);
            }
        });
    }
}
