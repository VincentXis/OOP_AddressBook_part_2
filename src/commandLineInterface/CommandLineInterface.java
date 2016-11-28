package commandLineInterface;


import contactRegister.Register;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandLineInterface {

    // runCLI() and the autoSave thread will run while run equals true
    private boolean run = true;
    private static final Logger log = Logger.getLogger(CommandLineInterface.class.getName());
    private Register reg = new Register();

    public CommandLineInterface() {
        runCLI();
    }

    /**
     * FN-10: AutoSave Thread
     */
    private Thread autoSaveThread = new Thread(() -> {
        log.info("Auto save thread started.");
        while (run) {
            try {
                Thread.sleep(5_000);
                reg.saveContactList();
            } catch (Exception e) {
                log.log(Level.SEVERE, "An error occurred in the AutoSave Thread: ", e);
            }
        }
        log.info("Auto save thread ended.");
        System.out.println("Thank you and good bye.");
    });

    /**
     * The while inside the while prevents add, search and delete from receiving the wrong input parameters.
     */
    private void runCLI() {
        log.info("runCli started");
        System.out.println("Welcome");
        autoSaveThread.start();

        String input;
        String[] inputSplit;
        while (run) {
            input = readUserInput();
            inputSplit = input.split(" ");

            while ((inputSplit[0].toLowerCase().equals("add") && inputSplit.length < 4) || ((inputSplit[0].toLowerCase().equals("search") || inputSplit[0].toLowerCase().equals("delete")) && inputSplit.length < 2)) {

                if (inputSplit[0].toLowerCase().equals("add")) {
                    System.out.println("To add a new contact you need to enter a the first and last name of\nthe person plus their e-mail address");
                    log.info("User failed to enter the correct input parameters to add a new contact");
                } else if (inputSplit[0].toLowerCase().equals("search")) {
                    System.out.println("To search for a contact in the list type: Search plus the first letters\nof the contacts first or last name");
                    log.info("User failed to enter the correct input parameters for a search");
                } else {
                    System.out.println("To delete a contact from your saved contacts enter the full ID of that contact");
                    log.info("User failed to enter the correct input parameters to delete a contact");
                }
                input = readUserInput();
                inputSplit = input.split(" ");
            }

            readInputCommand(inputSplit);
        }
        reg.saveContactList();

        log.info("runCli finished running.");
    }

    private void readInputCommand(String[] inputArray) {
        if (inputArray[0].toLowerCase().equals("end") || inputArray[0].toLowerCase().equals("exit")) {
            inputArray[0] = "quit"; // Temporary exit commands
        }
        try {
            switch (inputArray[0].toLowerCase()) {
                case "add":
                    reg.addContactToList(inputArray);
                    break;
                case "list":
                    reg.printContactList();
                    break;
                case "search":
                    reg.searchContact(inputArray[1]);
                    break;
                case "delete":
                    reg.deleteContactFromList(inputArray[1]);
                    break;
                case "help":
                    commandOptionList();
                    break;
                case "quit":
                    flipSwitch();
                    break;
                default:
                    System.out.println("Invalid input command: " + inputArray[0] +
                            "\nPlease try again, or type: \"help\" for a list of available commands");
                    log.info("User input was invalid, no operation was executed. User input was: " + inputArray[0]);
                    break;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "An error occurred: " + e);
        }
    }

    private void commandOptionList() {
        System.out.format("%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n", "Listing all available input commands:",
                "Add:    add a new contact to list", "List:   show all contacts in list", "Delete: remove a contact from list",
                "Search: find contact/s in list ", "Help:   to get here, lists all available commands", "Quit:   exit the application"
        );
        log.info("User requested to see a list of available commands");
    }


    private boolean flipSwitch() {
        System.out.println("Shutting down application, this may take a few seconds\nwaiting for active processes to finish:");
        log.info("User requested to exit the application, the run boolean will now be inverted");
        return run = !run;
    }


    private String readUserInput() {
        return new Scanner(System.in).nextLine();
    }
}
