package example;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main extends AbstractJavaFxApplicationSupport {

	public static void main(String[] args) {
		launchApp(Main.class, HomeView.class, args);
	}
}
