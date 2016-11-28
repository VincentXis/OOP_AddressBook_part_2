import commandLineInterface.CommandLineInterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

public class AddressBookMain {
    public static void main(String[] args) {
        loggerSetup();
        new CommandLineInterface();
    }

    /**
     * I decided to keep the logging.properties file in src, since im only sending the src folder.
     * And my file is slightly different from the one provided initially.
     * the log file does not write anything to the console, and does not overwrite the file each time its run.
     * however like your file, it does log to a folder called "logs" in the project folder.
     */
    private static void loggerSetup() {
        String loggingFilePath = "src/logging.properties";
        try (FileInputStream is = new FileInputStream(loggingFilePath)) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            throw new RuntimeException("Could not load log properties.", e);
        }
    }
}
