package listFileHandler;

import contactObject.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListFileHandler {

    private File savedContacts = new File("src\\listFileHandler\\savedContactList.data");
    private static final Logger log = Logger.getLogger(ListFileHandler.class.getName());

    /**
     * Save ContactList to disk
     * @param listOfContacts - copy of contact list
     */
    public synchronized void saveListToDisk(ArrayList<Contact> listOfContacts) {
        try (FileOutputStream fos = new FileOutputStream(savedContacts);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(listOfContacts);
            log.info("Contact list was saved to disk");
        } catch (IOException e) {
            log.log(Level.SEVERE, "Save method was unable to finish, an error occurred: ", e);
        }
    }

    /**
     * ┻━┻︵ \(°□°)/ ︵ ┻━┻
     */
    @SuppressWarnings("unchecked")
    public List<Contact> loadListFromFile() {
        if (savedContacts.exists()) {
            try (FileInputStream is = new FileInputStream(savedContacts);
                 ObjectInputStream ois = new ObjectInputStream(is)) {
                return (List<Contact>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                log.log(Level.SEVERE, "List was unable to be loaded from file because of an error: ", e);
            }
        }
        log.log(Level.INFO, "Unable to load list from file.");
        return new ArrayList<>();
    }
}

