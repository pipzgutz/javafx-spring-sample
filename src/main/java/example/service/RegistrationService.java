package example.service;

import example.dao.RegistrationDao;
import example.entity.Attendee;
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

    public boolean save(Attendee attendee) {
        String firstName = attendee.getFirstName();
        String lastName = attendee.getLastName();

        if (StringUtils.isNotBlank(firstName) && StringUtils.isNotBlank(lastName)) {
            Attendee searchedAttendee = registrationDao.findFirstByFirstNameAndLastName(firstName.trim(), lastName.trim());

            if (searchedAttendee == null) {
                registrationDao.save(attendee);
            }

            return true;
        }

        return false;
    }
}
