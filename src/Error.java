/**
 * The abstract class containing a function for handling errors.
 */
public abstract class Error {

    /**
     * Prints out the error message to the console in an appropriate way and exits the program.
     * @param text  Error message to be shown.
     */
    public static void errorMessage(String text) {
        System.out.println("ERROR: " + text);
        System.out.println("Exiting...");
        System.exit(-1);
    }

}
