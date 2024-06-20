/**
 * The class whose instances define an input.
 */
public class InputHelper {
    private int height, width;
    private boolean showHint;
    private String language;
    private String[] words;

    /**
     * Constructor for an InputHelper, deriving an input definition from the given input array.
     * @param input String array of lines, derived from the input file.
     */
    public InputHelper(String[] input) {

        //Errors below are self-explanatory.

        if (input.length < 4){
            Error.errorMessage("Invalid input file (Not enough arguments or no words at all)");
        }

        String[] size = input[0].split("x");
        try {
            height = Integer.parseInt(size[0]);
            width = Integer.parseInt(size[1]);
        } catch (Exception e){
            Error.errorMessage("Invalid input file (Invalid height and/or width values)");
        }

        if (Alphabets.ALPHABETS.containsKey(input[1])){
            language = input[1];
        } else {
            Error.errorMessage("Invalid input file (Given language is not supported or does not exist at all)");
        }

        if (input[2].equals("Yes")) {
            showHint = true;
        } else if (input[2].equals("No")){
            showHint = false;
        } else {
            Error.errorMessage("Invalid input file (Invalid data for showing hints)");
        }

        words = new String[input.length - 3];

        for (int i = 3; i < input.length; i++) {
            words[i - 3] = input[i];
        }
    }

    /**
     * @return The height value of the input in characters.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return The width value of the input in characters.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The boolean value of whether the solution will be shown or not.
     */
    public boolean isShowHint() {
        return showHint;
    }

    /**
     * @return The String array of all the words to be placed in the puzzle.
     */
    public String[] getWords() {
        return words;
    }

    /**
     * @return The language representative synonym String.
     */
    public String getLanguage() {
        return language;
    }
}
