import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The class representing a generated Word search grid, containing all its items and settings.
 */
public class WordSearchGrid implements Alphabets{
    private List<Word> wordList;
    private String[][] gridArray, hintArray;
    private String lan;
    private boolean isShowHint;

    /**
     * Constructor for a Word search grid instance.
     * @param words         String array of words to be placed.
     * @param height        Height of the grid, in characters.
     * @param width         Width of the grid, in characters.
     * @param lan           Language of the puzzle, currently either "EN" or "TR".
     * @param isShowHint    Boolean value of whether the solution is to be shown after the puzzle.
     */
    public WordSearchGrid(String[] words, int height, int width, String lan, boolean isShowHint) {

        this.lan = lan;

        /* If either of the height and width values are small for the longest word to fit in, the approved sizes
        are updated as one character greater than the longest word's length. */
        int approvedHeight, approvedWidth;
        int maxLength = Word.longestWordLength(words);
        approvedHeight = maxLength >= height ? maxLength + 1 : height;
        approvedWidth = maxLength >= width ? maxLength + 1 : width;

        wordList = new ArrayList<>();
        gridArray = new String[approvedHeight][approvedWidth];
        hintArray = new String[approvedHeight][approvedWidth];
        this.isShowHint = isShowHint;

        /* More than 50 tries per word on average will be considered as an error, assuming there are no more places left
         in the puzzle to place the words.
         Personal note: To improve the program, a backtracking algorithm can be implemented where if there are enough
         character spaces for all the words/characters to fit in, a limiting placement of a word can be replaced with a
         fitting one, but that's just an idea crossed my mind. Maybe I will find a way to implement it in the future.
         */
        final int maxAttemptCount = words.length * 50;
        int usedAttempts = 0;


        for (String word : words) {
            boolean falselyOverlapped;
            Word currentWord;
            do { //Trial and error for random placements.

                if (usedAttempts > maxAttemptCount){ //Test before each loop
                    Error.errorMessage("Maximum amount of trials exceeded (either there are too many words or too small grid size, or by a small chance the randomizer insisted on finding unfit word placements)");
                }

                falselyOverlapped = false;
                currentWord = new Word(word, approvedHeight, approvedWidth, this.lan);
                if (wordList.isEmpty()) { //Skip the rest of the do-while loop if this is the first word.
                    break;
                }

                /* At the end of the below loop, if the newly created word is falsely overlapping any of the words,
                 falselyOverlapped will be assigned true, but if it does not falsely overlap with neither of the words,
                 it will remain false, determining if the placement is fit or not. */
                for (Word word1 : wordList) {
                    if (!currentWord.overlappable(word1)) {
                        falselyOverlapped = true;
                    }
                }

                usedAttempts++;
            } while (falselyOverlapped);
            wordList.add(currentWord);
        }

        //Place the words first.
        for (Word word : wordList) {
            for (Map.Entry<Integer[], String> entry : word.getMapRepresentation().entrySet()) { //Place each character of the word to its location.

                gridArray[entry.getKey()[0]][entry.getKey()[1]] = entry.getValue();

                //In the word-placing part, place all the characters in lowercase letters.
                hintArray[entry.getKey()[0]][entry.getKey()[1]] = entry.getValue().toLowerCase(new Locale(this.lan));

            }
        }

        //Fill the remaining spaces with random characters from set alphabet.
        for (int i = 0; i < approvedHeight; i++){
            for (int j = 0; j < approvedWidth; j++){
                if (gridArray[i][j] == null){
                    gridArray[i][j] = Randomizer.randomElement(ALPHABETS.get(lan));
                    hintArray[i][j] = gridArray[i][j];
                }
            }
        }
    }

    /**
     * Returns this Word search puzzle, its words contained, and its solution (if isShowHint is set to true) in a String.
     *
     * @return The puzzle in a String with the words to find printed below it, and if isShowHint is set to true,
     *         the solution in lowercase letters.
     */
    public String toString(){
        String output = "";

        //Add all the elements into the String output.
        for (int i = 0; i < gridArray.length; i++){
            for (int j = 0; j < gridArray[i].length; j++){
                output += j == gridArray[i].length - 1 ? gridArray[i][j] : gridArray[i][j] + " ";
            }
            output += i == gridArray.length - 1 ? "" : "\n";
        }

        output += "\n\n";

        //Add all the words to the output, listing them in lines.
        for (int i = 0; i < wordList.size(); i++){
            output += i != wordList.size() - 1 ? String.format("%s\n", wordList.get(i).getWord()) : wordList.get(i).getWord();
        }

        if (isShowHint) {
            output += "\n\n------------------------------\n\nThe solutions (in lowercase):\n\n";

            // Add all the elements of hintArray to the output, showing the words in lowercase letters.
            for (int i = 0; i < hintArray.length; i++){
                for (int j = 0; j < hintArray[i].length; j++){
                    output += j == hintArray[i].length - 1 ? hintArray[i][j] : hintArray[i][j] + " ";
                }
                output += i == hintArray.length - 1 ? "" : "\n";
            }
        }

        return output;
    }
}
