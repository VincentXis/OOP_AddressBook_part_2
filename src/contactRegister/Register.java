package contactRegister;

import contactObject.Contact;
import listFileHandler.ListFileHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Register {
    private ListFileHandler clf = new ListFileHandler();
    private List<Contact> contactList;

    /**
     * The constructor
     */
    public Register() {
        this.contactList = clf.loadListFromFile();
    }

    /**
     * XFN-3: Sort contacts in alphabetical order
     */
    private List<Contact> sortByFirstName() {
        List<Contact> sortedContactList = new ArrayList<>(contactList);
        sortedContactList.sort(Comparator.comparing(contact -> contact.getFirstName().toLowerCase()));
        return sortedContactList;
    }

    /**
     * FN-2: Add new Contact to register
     */
    public void addContactToList(String[] inputArray) {
        contactList.add(new Contact(inputArray[1], inputArray[2], inputArray[3]));
        System.out.println(inputArray[1] + " " + inputArray[2] + " was added to your contacts");
    }

    /**
     * FN-3: List all contacts
     */
    public void printContactList() {
        System.out.println("Listing all contacts:");
        sortByFirstName().forEach(System.out::println);
        System.out.println("There are: " + contactList.size() + " saved contact/s in the list");
    }

    /**
     * FN-4: Search for contacts in list
     */
    public void searchContact(String query) {
        List<Contact> tmpList = new ArrayList<>();
        sortByFirstName().stream().filter(contact -> contact.getFirstName().toLowerCase().startsWith(query) || contact.getLastName().toLowerCase().startsWith(query)
        ).forEach(tmpList::add);
        System.out.println(tmpList.size() + " contact/s matched your search");
        if (!tmpList.isEmpty()) {
            tmpList.forEach(System.out::println);
        }
        tmpList = null;
    }

    /**
     * Delete a contact
     */
    public void removeContactFromList(String idStringToMatch) {
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getUUIDtoString().equals(idStringToMatch)) {
                System.out.format("Deleting contact matching input,\ncontact name: %s %s%n",
                        contactList.get(i).getFirstName(), contactList.get(i).getLastName());
                contactList.remove(i);
                return;
            }
        }
        System.out.println("No contact with that id found. please enter the UUID of an existing contact.");
    }

    /**
     * Part of XFN-2.1: (Save ContactList to file)
     */
    public void saveContactList() {
        clf.saveListToDisk(contactList);
    }
}

