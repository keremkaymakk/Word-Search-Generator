/**
 * The abstract class containing randomizing methods.
 */
public abstract class Randomizer{
    /**
     * Returns a random integer in the bound [lower, upper).
     *
     * @param lower Lower integer bound, contained in the randomizing range.
     * @param upper Upper integer bound, not contained in the randomizing range.
     * @return A random integer within the given bound.
     */
    public static int randomNumber(int lower, int upper){
        double randomMultiplier = Math.random(); //In the range of [0.0, 1.0)
        int randomNumber = (int)Math.floor((upper - lower) * randomMultiplier + lower);
        return randomNumber;
    }

    /**
     * Returns a random integer in the bound [0, upper).
     *
     * @param upper Upper integer bound, not contained in the randomizing range.
     * @return A random integer greater than or equal to 0 and less than the upper bound.
     */
    public static int randomNumber(int upper){
        return randomNumber(0, upper);
    }

    /**
     * Returns a random element within the given array.
     *
     * @param array The array to pick random element from.
     * @return A random element inside the array.
     * @param <E> Element type of the array.
     */
    public static <E> E randomElement(E[] array){
        return array[randomNumber(array.length)];
    }
}
