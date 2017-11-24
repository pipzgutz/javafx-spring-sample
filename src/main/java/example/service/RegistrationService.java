package example.service;

import example.dao.RegistrationDao;
import example.entity.Attendee;
import example.resp.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Philip Mark Gutierrez <pgutierrez@owens.com>
 * @version 1.0
 * @since November 19, 2017
 */
@Service
public class RegistrationService {
    @Autowired
    private RegistrationDao registrationDao;

    public Response save(Attendee attendee) {
        String firstName = attendee.getFirstName();
        String lastName = attendee.getLastName();
        String organization = attendee.getOrganization();
        String email = attendee.getEmail();
        String phoneNumber = attendee.getPhoneNumber();
        String lookingFor = attendee.getLookingFor();

        if (StringUtils.isNotBlank(firstName) && StringUtils.isNotBlank(lastName)
                && StringUtils.isNotBlank(organization) && StringUtils.isNotBlank(email) && StringUtils.isNotBlank(phoneNumber)
                && StringUtils.isNotBlank(lookingFor)) {
            Attendee searchedAttendee = registrationDao.findFirstByFirstNameAndLastName(firstName.trim(), lastName.trim());

            if (searchedAttendee == null) {
                registrationDao.save(attendee);
            } else {
                return new Response("That name has already been registered, kindly ask for assistance to edit that entry", false);
            }

            return new Response("Thank you for registering!", true);
        }

        return new Response("Kindly fillup all fields", false);
    }
}
