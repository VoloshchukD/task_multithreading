package by.epamtc.util;

public class Randomizer {

    private static final int LOWER_BORDER = 0;

    public static int generateNumber(int upperBorder) {
        int randomNumber = LOWER_BORDER + (int) (Math.random() * (upperBorder - 1));
        return randomNumber;
    }

}
