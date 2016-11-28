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

    private void runCLI() {
        log.info("runCli started");
        System.out.println("Welcome");

        new Thread(() -> {
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
            System.out.println("the last processes has finished running,\nready for the main process to finish.\nGood bye.");
        }).start();

        String input;
        String[] inputSplit;
        while (run) {
            input = readUserInput();
            inputSplit = input.split(" ");
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
            switch (inputArray[0]) {
                case "add":
                    if (inputArray.length < 4){
                        System.out.println("To add a new contact you need to enter a the first and last name of\nthe person plus their e-mail address");
                        log.info("User failed to enter the correct input parameters to add a new contact");
                        break;
                    }
                    reg.addContactToList(inputArray);
                    break;
                case "list":
                    reg.printContactList();
                    break;
                case "search":
                    if (inputArray.length < 2){
                        System.out.println("To search for a contact in the list type: Search plus the first letters\nof the contacts first or last name");
                        log.info("User failed to enter the correct input parameters for a search");
                        break;
                    }
                    reg.searchContact(inputArray[1]);
                    break;
                case "delete":
                    if (inputArray.length < 2){
                        System.out.println("To delete a contact from your saved contacts enter the full ID of that contact");
                        log.info("User failed to enter the correct input parameters to delete a contact");
                        break;
                    }
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
        System.out.format("%s\n%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n",
                "The input for a command has to be lowercase to register","List of all available commands:",
                "add:    add a new contact to list", "list:   show all contacts in list", "delete: remove a contact from list",
                "search: find contact/s in list ", "help:   to get here, lists all available commands", "quit:   exit the application"
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
