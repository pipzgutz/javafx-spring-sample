package example.dao;

import example.entity.Attendee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Philip Mark Gutierrez <pgutierrez@owens.com>
 * @version 1.0
 * @since November 20, 2017
 */
public interface RegistrationDao extends CrudRepository<Attendee, Long> {
    Attendee findFirstByFirstNameAndLastName(String firstName, String lastName);
    List<Attendee> findAll();
}
