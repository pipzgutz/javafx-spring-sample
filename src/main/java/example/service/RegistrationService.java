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

        if (StringUtils.isNotBlank(firstName) && StringUtils.isNotBlank(lastName)
                && StringUtils.isNotBlank(organization) && StringUtils.isNotBlank(email) && StringUtils.isNotBlank(phoneNumber)
                && StringUtils.isNotBlank(lookingFor) && StringUtils.isNotBlank(trainingsInterestedIn)) {
            Attendee searchedAttendee = registrationDao.findFirstByFirstNameAndLastName(firstName.trim(), lastName.trim());

            if (searchedAttendee == null) {
                registrationDao.save(attendee);
            } else {
                return new Response("That name has already been registered, kindly ask for assistance to edit that entry", false);
            }

            return new Response("Thank you for registering! Please, enjoy the event", true);
        }

        String message = "Kindly fillup all fields";

        if (StringUtils.isBlank(lookingFor)) {
            message = "We are interested on what you are looking for";
        } else if (StringUtils.isBlank(trainingsInterestedIn)) {
            message = "Select at least one training you are interested in";
        }

        return new Response(message, false);
    }
}
