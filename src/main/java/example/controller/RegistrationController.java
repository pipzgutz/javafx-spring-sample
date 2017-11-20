package example.controller;

import de.felixroske.jfxsupport.FXMLController;
import example.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Philip Mark Gutierrez <pgutierrez@owens.com>
 * @version 1.0
 * @since November 19, 2017
 */
@FXMLController
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

}
