package example.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.felixroske.jfxsupport.FXMLController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import example.entity.Attendee;
import example.resp.Response;
import example.service.ExportService;
import example.service.RegistrationService;
import example.util.UIUtil;
import example.validator.EmailFieldValidator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
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
    private JFXTextField firstNameField;
    @FXML
    private JFXTextField lastNameField;
    @FXML
    private JFXTextField organizationField;
    @FXML
    private JFXTextField emailAddressField;
    @FXML
    private JFXTextField phoneNumberField;
    @FXML
    private JFXComboBox lookingFor;
    @FXML
    private JFXListView<String> phpListView;
    private Map<String, ObservableValue<Boolean>> phpTrainings = new HashMap<>();
    @FXML
    private JFXListView<String> dotNetListView;
    private Map<String, ObservableValue<Boolean>> dotNetTrainings = new HashMap<>();
    @FXML
    private JFXListView<String> javaListView;
    private Map<String, ObservableValue<Boolean>> javaTrainings = new HashMap<>();

    public void register(ActionEvent event) {
        // validate the fields
        validateFields();

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
            clearAllFields();
        } else {
            UIUtil.showAlert(Alert.AlertType.ERROR, "Registration Failed", "", response.getMessage());
        }
    }

    private void validateFields() {
        firstNameField.validate();
        lastNameField.validate();
        organizationField.validate();
        emailAddressField.validate();
        phoneNumberField.validate();
    }

    public void clearAllFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        organizationField.setText("");
        emailAddressField.setText("");
        phoneNumberField.setText("");
        lookingFor.getSelectionModel().clearSelection();

        phpListView.getItems().clear();
        dotNetListView.getItems().clear();
        javaListView.getItems().clear();
        initAllTrainings();

        firstNameField.requestFocus();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initAllTrainings();

        addRequiredValidator(firstNameField);
        addRequiredValidator(lastNameField);
        addRequiredValidator(organizationField);
        addRequiredValidator(emailAddressField);
        addEmailValidator(emailAddressField);
        addRequiredValidator(phoneNumberField);

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

        firstNameField.requestFocus();
    }

    private void addEmailValidator(JFXTextField textField) {
        EmailFieldValidator emailFieldValidator = new EmailFieldValidator();
        textField.getValidators().add(emailFieldValidator);
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                textField.validate();
            }
        });
    }

    private void addRequiredValidator(JFXTextField textField) {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Field is required");

        textField.getValidators().add(requiredFieldValidator);
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                textField.validate();
            }
        });
    }

    private void initAllTrainings() {
        initTrainings(phpListView, phpTrainings, Arrays.asList("Core", "Falcon"));
        initTrainings(dotNetListView, dotNetTrainings, Arrays.asList("Core", "Full Stack"));
        initTrainings(javaListView, javaTrainings, Arrays.asList("Core", "Web", "Spring", "Full Stack"));
    }

    private void initTrainings(ListView<String> listView, Map<String, ObservableValue<Boolean>> trainings, List<String> contents) {
        contents.forEach(content -> trainings.put(content, new SimpleBooleanProperty(false)));
        listView.getItems().addAll(trainings.keySet());
        listView.setCellFactory(CheckBoxListCell.forListView(trainings::get));
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
