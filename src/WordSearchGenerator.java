/**
 * Main class of this project.
 */
public class WordSearchGenerator {

    /**
     * Main method of this project.
     *
     * Creates a Word search puzzle from given settings and words in an input file, then writes it both to the console
     * and to the output file.
     *
     * Takes two command line arguments, the first being the path to the input file and the second being to the output file.
     * Input file contains the settings in the first three lines:
     * 1- Size of the puzzle to be generated (e.g., 20x20)
     * 2- The language set of the puzzle (Supported are: EN - English and TR - Turkish)
     * 3- Whether the solution is requested to be shown after the puzzle (simply Yes or No)
     * Then in the next lines, words follow.
     *
     * If output file doesn't exist, it is created, and it is cleared in first parts of the program, before writing the output.
     *
     * @param args  Command line arguments.
     */
    public static void main(String[] args) {

        if (args.length != 2) {
            Error.errorMessage("Erroneous number of command line arguments!");
        }

        InputHelper input = new InputHelper(FileInput.readFile(args[0], true, true));

        FileOutput.writeToFile(args[1], "", false, false); //Clear the output file.

        WordSearchGrid puzzle = new WordSearchGrid(input.getWords(), input.getHeight(), input.getWidth(), input.getLanguage(), input.isShowHint());

        System.out.print(puzzle);
        FileOutput.writeToFile(args[1], puzzle.toString(), true, false);
    }
}
