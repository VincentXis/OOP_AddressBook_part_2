package contactObject;

import java.io.Serializable;
import java.util.UUID;

public class Contact implements Serializable {
    private String firstName;
    private String lastName;
    private String eMail;
    private UUID uuid;


    public Contact(String firstName, String lastName, String eMail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.uuid = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return String.format("Contact UUID: %s\n  First name: %s\n   Last name: %s\n\t  E-mail: %s\n",
                uuid, firstName, lastName, eMail);
    }

    // Getters necessary for search and destroy
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUUIDtoString() {
        return uuid.toString();
    }
}
