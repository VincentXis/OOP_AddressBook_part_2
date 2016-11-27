package commandLineInterface;


import contactRegister.Register;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandLineInterface {
    // Instance of Register
    private Register reg = new Register();
    // runCLI() will run while run equals true
    private boolean run = true;
    // Logger
    private static final Logger log = Logger.getLogger(CommandLineInterface.class.getName());
    /**
     * The constructor calls runCLI when instantiated
     */
    public CommandLineInterface() {
        runCLI();
    }

    private void runCLI() {
        log.info("runCli started");
        new Thread(() -> {
            log.info("Auto save thread started.");
            while (run){
                try {
                    Thread.sleep(5_000);
                    reg.saveContactList();

                } catch (InterruptedException e) {
                    log.log(Level.SEVERE, "Sleep failed",e);
                }
            }
            log.info("Auto save thread ended.");
        }).start();
        System.out.println("Welcome");

        String input;
        String[] inputSplit;
        while (run) {
            input = readUserInput();
            inputSplit = input.split(" ");
            readInputCommand(inputSplit);
        }
        reg.saveContactList();
        System.out.println("Good bye");
        log.info("runCli finished running.");
    }

    private void readInputCommand(String[] inputArray) {
        if (inputArray[0].toLowerCase().equals("end") || inputArray[0].toLowerCase().equals("exit")) {
            inputArray[0] = "quit"; // Temporary exit commands
        }
        try {
            switch (inputArray[0].toLowerCase()) {
                case "add": // feedback done
                    reg.addContactToList(inputArray);
                    break;
                case "delete": // deleted contact + get id
                    reg.removeContactFromList(inputArray[1]);
                    break;
                case "list": // listing all existing contacts
                    reg.printContactList();
                    break;
                case "search": // feedback done
                    reg.searchContact(inputArray[1]);
                    break;
                case "help": // feedback done
                    commandOptionList();
                    break;
                case "quit": // feedback done
                    flipSwitch();
                    break;
                default:
                    System.out.println("Invalid command: " + inputArray[0] +
                            "\nPlease try again, or type \"help\" for available commands");
                    break;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "User input did not meet the requirement to preform an operation");
        }
    }

    private void commandOptionList() {
        System.out.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n", "Listing all available input commands:", "Add:    add a new contact to list",
                "Delete: remove a contact from list", "List:   show all contacts in list", "Search: find contact/s in list ",
                "Quit:   exit the application", "Help:   to get here, lists all available commands");
    }


    private boolean flipSwitch() {
        System.out.println("Shutting down application, finishing all active threads processes.");
        return run = !run;
    }


    private String readUserInput() {
        return new Scanner(System.in).nextLine();
    }
}
