package by.epamtc.util;

public class Randomizer {

    private static final int LOWER_BORDER = 0;

    private static final double BOOLEAN_PROBABILITY = 0.5;

    public static int generateNumber(int upperBorder) {
        int randomNumber = LOWER_BORDER + (int) (Math.random() * (upperBorder - 1));
        return randomNumber;
    }

    public static boolean generateBoolean() {
        return Math.random() < BOOLEAN_PROBABILITY;
    }

}
