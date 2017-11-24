package example.service;

import example.dao.RegistrationDao;
import example.entity.Attendee;
import example.resp.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Philip Mark Gutierrez <pgutierrez@owens.com>
 * @version 1.0
 * @since November 19, 2017
 */
@Service
public class RegistrationService {
    @Autowired
    private RegistrationDao registrationDao;

    public List<Attendee> findAll() {
        return registrationDao.findAll();
    }

    public Response save(Attendee attendee) {
        String firstName = attendee.getFirstName();
        String lastName = attendee.getLastName();
        String organization = attendee.getOrganization();
        String email = attendee.getEmail();
        String phoneNumber = attendee.getPhoneNumber();
        String lookingFor = attendee.getLookingFor();
        String trainingsInterestedIn = attendee.getTrainingsInterestedIn();

        // validate the fields
        boolean isBasicFieldsBlank = StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName)
                || StringUtils.isBlank(organization) || StringUtils.isBlank(email) || StringUtils.isBlank(phoneNumber);

        if (isBasicFieldsBlank || StringUtils.isBlank(lookingFor)
                || StringUtils.isBlank(trainingsInterestedIn)) {

            String message = "Kindly fill up all fields.";

            if (!isBasicFieldsBlank) {
                if (StringUtils.isBlank(lookingFor)) {
                    message = "Kindly tell us, what are you looking for?";
                } else if (StringUtils.isBlank(trainingsInterestedIn)) {
                    message = "Kindly select at least one training you are interested in.";
                }
            }

            return new Response(message, false);
        }

        Attendee searchedAttendee = registrationDao.findFirstByFirstNameAndLastName(firstName.trim(), lastName.trim());

        if (searchedAttendee == null) {
            registrationDao.save(attendee);
        } else {
            return new Response("That name has already been registered, kindly ask for assistance to edit that entry", false);
        }

        return new Response("Thank you for registering! Please, enjoy the event", true);
    }
}
