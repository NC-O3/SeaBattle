package com.company;

public class Checker {

    public static boolean checkInput(String input) {
        String regex = "[A-Z][0-9]+\\s+[A-Z][0-9]+";

        if (!input.matches(regex)) {
            return false;
        }

        return true;
    }

    private static int convertPosition(char input) {
        return (int) input - 64;
    }

    public static int[] transformInCoordinates(String input) {
        String[] coordinates = input.split("\\s+");

        int[] coordinatesInteger = new int[4];
        char[] number1 = new char[coordinates[0].length()];
        coordinates[0].getChars(0, coordinates[0].length(), number1, 0);
        char[] number2 = new char[coordinates[1].length()];
        coordinates[1].getChars(0, coordinates[1].length(), number2, 0);

        coordinatesInteger[0] = convertPosition(number1[0]);
        int numberFirst = 0;
        if (number1.length <= 2) {
            numberFirst = Character.getNumericValue(number1[1]);
        } else {
            numberFirst = Character.getNumericValue(number1[1]);
            for (int i = 2; i < number1.length; i++) {
                numberFirst = numberFirst * 10 + Character.getNumericValue(number1[i]);
            }
        }
        coordinatesInteger[1] = numberFirst;

        coordinatesInteger[2] = convertPosition(number2[0]);
        int numberSecond = 0;
        if (number2.length <= 2) {
            numberSecond = Character.getNumericValue(number2[1]);
        } else {
            numberSecond = Character.getNumericValue(number2[1]);
            for (int i = 2; i < number2.length; i++) {
                numberSecond = numberSecond * 10 + Character.getNumericValue(number2[i]);
            }
        }
        coordinatesInteger[3] = numberSecond;

        return coordinatesInteger;
    }

}
