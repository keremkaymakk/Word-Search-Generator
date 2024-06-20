import java.util.*;

/**
 * The instance class containing a word, its array representation, its map representation where keys are locations in
 * 2D array, its origin and its language.
 */
public class Word {
    private final String word, wordWithSpaces, lang;
    private Integer[] origin;

    /**
     * Key is {@link Integer}[] with length 2, representing the array location of the character and value is {@link String} containing
     * the character in that location.
     */
    private HashMap<Integer[], String> mapRepresentation;

    /**
     * Constructor for a Word object. It creates different instance types for different rotations.
     *
     * @param word     The word as a simple string.
     * @param rotation The rotation value, an integer between [0,7], increasing like the angle in polar coordinates.
     * @param height   The height value of the puzzle, in characters, to determine origin.
     * @param width    The width value of the puzzle, in characters, to determine origin.
     * @param lang     The language representative synonym String, to use in lowercase/uppercase operations.
     */
    private Word(String word, int rotation, int height, int width, String lang) {
        this.lang = lang;
        this.wordWithSpaces = word;
        this.word = discardSpaces(word.toUpperCase(new Locale(this.lang)));
        this.mapRepresentation = new HashMap<>();

        /*
         Below switch statement basically does these things based on different rotation values:
         1- Determine the origin (i.e. the location of the first letter of the word)
         2- Loop through the rectangular area that this word occupies, starting from the word's first letter,
            place the characters to their appropriate locations by putting them into the mapRepresentation.
         */
        switch (rotation) {
            case 0:
                this.origin = new Integer[]{Randomizer.randomNumber(height), Randomizer.randomNumber(width - this.word.length() + 1)};
                for (int j = 0; j < this.word.length(); j++) {
                    mapRepresentation.put(new Integer[]{origin[0], origin[1] + j}, Character.toString(this.word.charAt(j)));
                }
                break;
            case 1:
                this.origin = new Integer[]{Randomizer.randomNumber(this.word.length() - 1, height), Randomizer.randomNumber(width - this.word.length() + 1)};
                for (int i = this.word.length() - 1; i >= 0; i--) {
                    for (int j = 0; j < this.word.length(); j++) {
                        if (i == this.word.length() - j - 1) {
                            mapRepresentation.put(new Integer[]{origin[0] - this.word.length() + i + 1, origin[1] + j}, Character.toString(this.word.charAt(j)));
                        }
                    }
                }
                break;
            case 2:
                this.origin = new Integer[]{Randomizer.randomNumber(this.word.length() - 1, height), Randomizer.randomNumber(width)};
                for (int i = this.word.length() - 1; i >= 0; i--) {
                    mapRepresentation.put(new Integer[]{origin[0] - this.word.length() + i + 1, origin[1]}, Character.toString(this.word.charAt(this.word.length() - i - 1)));
                }
                break;
            case 3:
                this.origin = new Integer[]{Randomizer.randomNumber(this.word.length() - 1, height), Randomizer.randomNumber(this.word.length() - 1, width)};
                for (int i = this.word.length() - 1; i >= 0; i--) {
                    for (int j = this.word.length() - 1; j >= 0; j--) {
                        if (i == j) {
                            mapRepresentation.put(new Integer[]{origin[0] - this.word.length() + i + 1, origin[1] - this.word.length() + j + 1}, Character.toString(this.word.charAt(this.word.length() - i - 1)));
                        }
                    }
                }
                break;
            case 4:
                this.origin = new Integer[]{Randomizer.randomNumber(height), Randomizer.randomNumber(this.word.length() - 1, width)};
                for (int j = this.word.length() - 1; j >= 0; j--) {
                    mapRepresentation.put(new Integer[]{origin[0], origin[1] - this.word.length() + j + 1}, Character.toString(this.word.charAt(this.word.length() - j - 1)));
                }
                break;
            case 5:
                this.origin = new Integer[]{Randomizer.randomNumber(height - this.word.length() + 1), Randomizer.randomNumber(this.word.length() - 1, width)};
                for (int i = 0; i < this.word.length(); i++) {
                    for (int j = this.word.length() - 1; j >= 0; j--) {
                        if (i == this.word.length() - j - 1) {
                            mapRepresentation.put(new Integer[]{origin[0] + i, origin[1] - this.word.length() + j + 1}, Character.toString(this.word.charAt(this.word.length() - j - 1)));
                        }
                    }
                }
                break;
            case 6:
                this.origin = new Integer[]{Randomizer.randomNumber(height - this.word.length() + 1), Randomizer.randomNumber(width)};
                for (int i = 0; i < this.word.length(); i++) {
                    mapRepresentation.put(new Integer[]{origin[0] + i, origin[1]}, Character.toString(this.word.charAt(i)));
                }
                break;
            case 7:
                this.origin = new Integer[]{Randomizer.randomNumber(height - this.word.length() + 1), Randomizer.randomNumber(width - this.word.length() + 1)};
                for (int i = 0; i < this.word.length(); i++) {
                    for (int j = 0; j < this.word.length(); j++) {
                        if (i == j) {
                            mapRepresentation.put(new Integer[]{origin[0] + i, origin[1] + j}, Character.toString(this.word.charAt(j)));
                        }
                    }
                }
                break;
            default:
                //The code is not supposed to get here, since a rotation value must be given.
                System.out.println("An error has occured while running the program. Exiting...");
                System.exit(0);
        }
    }

    /**
     * Creates a Word instance with given parameters, randomizing the rotation value.
     *
     * @param word   The word as a simple string.
     * @param height The height value of the puzzle, in characters, to determine origin.
     * @param width  The width value of the puzzle, in characters, to determine origin.
     * @param lang   The language representative synonym String, to use in lowercase/uppercase operations.
     */
    public Word(String word, int height, int width, String lang) {
        this(word, Randomizer.randomNumber(8), height, width, lang);
    }

    /**
     * Determines if this word is not falsely overlapping (i.e. has two different character values at the same location)
     * the given other word.
     *
     * @param otherWord Other word to check if this word is falsely overlapping with.
     * @return If the two words are not overlapping or overlapping correctly, then true, otherwise false.
     */
    public boolean overlappable(Word otherWord) {
        for (Integer[] filledCoords : this.mapRepresentation.keySet()) {
            for (Integer[] otherFilledCoords : otherWord.mapRepresentation.keySet()) {
                if (Arrays.equals(filledCoords, otherFilledCoords)) {
                    if (!this.mapRepresentation.get(filledCoords).equals(otherWord.mapRepresentation.get(otherFilledCoords))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns the length of the longest world in a given String array.
     *
     * @param wordArray The String array to pick the longest word from.
     * @return The length of the longest word.
     */
    public static int longestWordLength(String[] wordArray) {
        int greatestLength = 0;
        for (String word : wordArray) {
            if (word.length() > greatestLength) {
                greatestLength = word.length();
            }
        }
        return greatestLength;
    }

    /**
     * @return Map representation of this word, Integer[2] location as keys and String character as values.
     */
    public HashMap<Integer[], String> getMapRepresentation() {
        return mapRepresentation;
    }

    /**
     * Takes the given text, removes all the spaces from it and returns the final product.
     * @param text  The String text to remove spaces from.
     * @return The String text with the spaces removed.
     */
    public static String discardSpaces(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Character c : text.toCharArray()) {
            if (!c.equals(' ')) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * @return The word as a simple string, with spaces.
     */
    public String getWord() {
        return this.wordWithSpaces.toUpperCase(new Locale(this.lang));
    }
}
