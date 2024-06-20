import java.util.HashMap;

/**
 * The interface containing the character sets for the alphabets, currently containing only English and Turkish.
 */
public interface Alphabets {
    HashMap<String, String[]> ALPHABETS = new HashMap<String, String[]>(){{
        put("EN", new String[]{
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
                "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        });
        put("TR", new String[]{
                "A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L",
                "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z"
        });
    }};
}
