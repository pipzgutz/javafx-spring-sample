package example.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Philip Mark Gutierrez <pgutierrez@owens.com>
 * @version 1.0
 * @since November 19, 2017
 */
@Entity
public class Attendee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "organization")
    private String organization;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "looking_for")
    private String lookingFor;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainings_interested_in")
    private List<Training> trainingsInterestedIn;

    public Attendee() {
    }

    public Attendee(String firstName, String lastName, String organization, String email, String phoneNumber, String lookingFor, List<Training> trainingsInterestedIn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.lookingFor = lookingFor;
        this.trainingsInterestedIn = trainingsInterestedIn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(String lookingFor) {
        this.lookingFor = lookingFor;
    }

    public List<Training> getTrainingsInterestedIn() {
        return trainingsInterestedIn;
    }

    public void setTrainingsInterestedIn(List<Training> trainingsInterestedIn) {
        this.trainingsInterestedIn = trainingsInterestedIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attendee)) return false;

        Attendee attendee = (Attendee) o;

        if (id != null ? !id.equals(attendee.id) : attendee.id != null) return false;
        if (firstName != null ? !firstName.equals(attendee.firstName) : attendee.firstName != null) return false;
        if (lastName != null ? !lastName.equals(attendee.lastName) : attendee.lastName != null) return false;
        if (organization != null ? !organization.equals(attendee.organization) : attendee.organization != null)
            return false;
        if (email != null ? !email.equals(attendee.email) : attendee.email != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(attendee.phoneNumber) : attendee.phoneNumber != null)
            return false;
        if (lookingFor != null ? !lookingFor.equals(attendee.lookingFor) : attendee.lookingFor != null) return false;
        return trainingsInterestedIn != null ? trainingsInterestedIn.equals(attendee.trainingsInterestedIn) : attendee.trainingsInterestedIn == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (lookingFor != null ? lookingFor.hashCode() : 0);
        result = 31 * result + (trainingsInterestedIn != null ? trainingsInterestedIn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", organization='" + organization + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", lookingFor='" + lookingFor + '\'' +
                ", trainingsInterestedIn=" + trainingsInterestedIn +
                '}';
    }
}
