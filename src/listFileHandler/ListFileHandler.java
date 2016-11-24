package listFileHandler;

import contactObject.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListFileHandler {
    private File savedContacts = new File("src\\listFileHandler\\savedContactList.data");

    public void saveListToDisk(List listOfContacts) {
        try (FileOutputStream fos = new FileOutputStream(savedContacts);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(listOfContacts);
        } catch (IOException e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }
}

