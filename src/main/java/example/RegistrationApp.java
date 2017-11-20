package example;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import example.view.RegistrationView;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RegistrationApp extends AbstractJavaFxApplicationSupport {

	public static void main(String[] args) {
		launchApp(RegistrationApp.class, RegistrationView.class, args);
	}
}
