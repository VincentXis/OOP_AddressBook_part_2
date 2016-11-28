package contactRegister;

import contactObject.Contact;
import listFileHandler.ListFileHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class Register {

    public Register() {
        this.contactList = clf.loadListFromFile();
    }
    private static final Logger log = Logger.getLogger(Register.class.getName());
    private ListFileHandler clf = new ListFileHandler();
    private List<Contact> contactList;

    /**
     * Sort contacts in alphabetical order
     */
    private List<Contact> sortByFirstName() {
        List<Contact> sortedContactList = new ArrayList<>(contactList);
        sortedContactList.sort(Comparator.comparing(contact -> contact.getFirstName().toLowerCase()));
        return sortedContactList;
    }

    public void addContactToList(String[] inputArray) {
        contactList.add(new Contact(inputArray[1], inputArray[2], inputArray[3]));
        System.out.println(inputArray[1] + " " + inputArray[2] + " was added to your contacts");
        log.info("new contact added to list");
    }

    /**
     * List all contacts, uses a sorted copy of contactList.
     */
    public void printContactList() {
        System.out.println("Listing all contacts:");
        sortByFirstName().forEach(System.out::println);
        System.out.println("There are: " + contactList.size() + " saved contact/s in the list");

        log.info("User requested all contacts to be listed");
    }

    /**
     * Search for contacts in list.
     * The method uses a sorted copy of contactList.
     * I wanted to print out the number of matches, and then each contact that matches.
     * but I couldn't figure out how to do that after the filter and before the foreach method.
     * therefore i used another list that receives matching contacts and prints them after the amount message.
     * log depends on result.
     */
    public void searchContact(String query) {
        int count = sortByFirstName().stream().filter(contact ->
                contact.getFirstName().toLowerCase().startsWith(query) || contact.getLastName().toLowerCase().startsWith(query))
                .mapToInt(contact -> (1)).sum();
        System.out.println(count + " contact/s matched your search");
        if (count > 0){
            sortByFirstName().stream().filter(contact ->
                    contact.getFirstName().toLowerCase().startsWith(query) || contact.getLastName().toLowerCase().startsWith(query))
                    .forEach(System.out::println);
            log.info("user found matching contact/s with the input query");
        } else {
            log.info("user found no matching contact/s with the input query ");
        }
    }

    /**
     * Delete a contact from list
     * depending on if the user gets a match on the provided ID number
     * different messages are presented to the user and different logs are recorded.
     */
    public void deleteContactFromList(String idStringToMatch) {
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getUUIDtoString().equals(idStringToMatch)) {
                System.out.format("Deleting contact matching the provided ID number\nContact name: %s %s\n",
                        contactList.get(i).getFirstName(), contactList.get(i).getLastName());
                contactList.remove(i);
                log.info("Contact matching user provided ID number was deleted from list");
                return;
            }
        }
        System.out.println("No contact with that id found. please enter the UUID of an existing contact.");
        log.info("No contact was found with the user provided ID to match, list remains untouched.");
    }

    public void saveContactList() {
        clf.saveListToDisk(new ArrayList<>(contactList));
    }
}

