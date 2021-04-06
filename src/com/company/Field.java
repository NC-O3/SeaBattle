package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Field {

    private int size = 10;
    private AircraftCarrier aircraftCarrier;
    private Battleship battleship;
    private Submarine submarine;
    private Cruiser cruiser;
    private Destroyer destroyer;

    public Field() {
    }

    public void displayField() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        int start = 65;
        for (int i = 1; i <= 10; i++) {
            System.out.print((char) start + " ");
            start++;
            for (int j = 1; j <= 10; j++) {
                System.out.print(whatIs(i, j) + " ");
            }
            System.out.println();
        }
    }

    private int getNextShip() {
        if (aircraftCarrier == null) {
            System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells): ");
            return 0;
        } else if (battleship == null) {
            System.out.println("Enter the coordinates of the Battleship (4 cells): ");
            return 1;
        } else if (submarine == null) {
            System.out.println("Enter the coordinates of the Submarine (3 cells): ");
            return 2;
        } else if (cruiser == null) {
            System.out.println("Enter the coordinates of the Cruiser (3 cells): ");
            return 3;
        } else if (destroyer == null) {
            System.out.println("Enter the coordinates of the Destroyer (2 cells): ");
            return 4;
        } else {
            return -1;
        }
    }

    private void buildNextShip(int headX, int headY, int tailX, int tailY, int number) throws InvalidCoordinatesException{
        if (verifyFuturePosition(headX, headY, tailX, tailY) == false) {
            throw new InvalidCoordinatesException("Error! You placed it too close to another one. Try again: ");
        }

        switch (number) {
            case 0:
                setAircraftCarrier(headX, headY, tailX, tailY);
                displayField();
                break;
            case 1:
                setBattleship(headX, headY, tailX, tailY);
                displayField();
                break;
            case 2:
                setSubmarine(headX, headY, tailX, tailY);
                displayField();
                break;
            case 3:
                setCruiser(headX, headY, tailX, tailY);
                displayField();
                break;
            case 4:
                setDestroyer(headX, headY, tailX, tailY);
                displayField();
                break;
        }
    }

    public void autoCompleter() throws InvalidCoordinatesException{

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            int shipNumber = getNextShip();

            while (shipNumber != -1) {
                String line = bufferedReader.readLine();
                while (!Checker.checkInput(line)) {
                    System.out.println("Incorrect input! Try again: ");
                    line = bufferedReader.readLine();
                }

                int[] coordinates = Checker.transformInCoordinates(line);
                try {
                    buildNextShip(coordinates[0], coordinates[1], coordinates[2], coordinates[3], shipNumber);
                } catch (InvalidCoordinatesException e) {
                    System.out.println(e.getMessage());
                }

                shipNumber = getNextShip();
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private boolean verifyFuturePosition(int headX, int headY, int tailX, int tailY) {

        int maxX = headX;
        int minX = tailX;
        if (maxX < minX) {
            maxX = tailX;
            minX = headX;
        }

        int maxY = headY;
        int minY = tailY;
        if (maxY < minY) {
            maxY = tailY;
            minY = headY;
        }

        boolean isOkay = true;

        for (int i = minX - 1; i <= maxX + 1; i++) {
            for (int j = minY - 1; j <= maxY + 1; j++) {
                if (i < 0 || i > 10 || j < 0 || j > 10) {
                    continue;
                }
                if (whatIs(i, j) != '~') {
                    isOkay = false;
                }
            }
        }

        return isOkay;

    }

    private void setAircraftCarrier(int headX, int headY, int tailX, int tailY) throws InvalidCoordinatesException{
        verifyData(headX, headY, tailX, tailY, "Aircraft Carrier", 5);
        aircraftCarrier = new AircraftCarrier(headX, headY, tailX, tailY);
    }

    private void setBattleship(int headX, int headY, int tailX, int tailY) throws InvalidCoordinatesException{
        verifyData(headX, headY, tailX, tailY, "Battleship", 4);
        battleship = new Battleship(headX, headY, tailX, tailY);
    }

    private void setSubmarine(int headX, int headY, int tailX, int tailY) throws InvalidCoordinatesException{
        verifyData(headX, headY, tailX, tailY, "Submarine", 3);
        submarine = new Submarine(headX, headY, tailX, tailY);
    }

    private void setCruiser(int headX, int headY, int tailX, int tailY) throws InvalidCoordinatesException{
        verifyData(headX, headY, tailX, tailY, "Cruiser", 3);
        cruiser = new Cruiser(headX, headY, tailX, tailY);
    }

    private void setDestroyer(int headX, int headY, int tailX, int tailY) throws InvalidCoordinatesException{
        verifyData(headX, headY, tailX, tailY, "Destroyer", 2);
        destroyer = new Destroyer(headX, headY, tailX, tailY);
    }

    private void verifyData(int headX, int headY, int tailX, int tailY, String name, int shipSize) throws InvalidCoordinatesException{
        if (headX < 0 || headX > 10) {
            throw new InvalidCoordinatesException("Error! The input is outside the field! Try again: ");
        } else if (headY < 0 || headY > 10) {
            throw new InvalidCoordinatesException("Error! The input is outside the field! Try again: ");
        } else if (tailX < 0 || tailX > 10) {
            throw new InvalidCoordinatesException("Error! The input is outside the field! Try again: ");
        } else if (tailY < 0 || tailY > 10) {
            throw new InvalidCoordinatesException("Error! The input is outside the field! Try again: ");
        }

        int size = determineSize(headX, headY, tailX, tailY);
        if (size == -1) {
            throw new InvalidCoordinatesException("Error! Wrong ship location! Try again: ");
        } else if (size != shipSize) {
            throw new InvalidCoordinatesException(String.format("Error! Wrong length of the %s! Try again: ", name));
        }
    }

    private int determineSize(int headX, int headY, int tailX, int tailY) {
        if (headX == tailX) {
            return Math.abs(headY - tailY) + 1;
        } else if (headY == tailY) {
            return Math.abs(headX - tailX) + 1;
        } else {
            return -1;
        }
    }

    private char verifyShip(int coordinateX, int coordinateY, Ship ship) {
        int[] health = ship.getHealth();
        if (ship.getHeadX() == ship.getTailX()) {
            if (coordinateX == ship.getHeadX() && (Math.abs(ship.getHeadY() - coordinateY)) < ship.getSize()) {
                if ((coordinateY >= ship.getHeadY() && coordinateY <= ship.getTailY()) || (coordinateY <= ship.getHeadY() && coordinateY >= ship.getTailY())) {
                    if (health[Math.abs(ship.getHeadY() - coordinateY)] == 1) {
                        return 'O';
                    } else {
                        return 'X';
                    }
                } else {
                    return '~';
                }
            } else {
                return '~';
            }
        } else if (ship.getHeadY() == ship.getTailY()) {
            if (coordinateY == ship.getHeadY() && (Math.abs(ship.getHeadX() - coordinateX)) < ship.getSize()) {
                if ((coordinateX >= ship.getHeadX() && coordinateX <= ship.getTailX()) || (coordinateX <= ship.getHeadX() && coordinateX >= ship.getTailX())) {
                    if (health[Math.abs(ship.getHeadX() - coordinateX)] == 1) {
                        return 'O';
                    } else {
                        return 'X';
                    }
                } else {
                    return '~';
                }
            } else {
                return '~';
            }
        } else {
            return '~';
        }
    }

    private char whatIs(int coordinateX, int coordinateY) {

        char finalResult = '~';
        char result;

        if (aircraftCarrier != null) {
            result = verifyShip(coordinateX, coordinateY, aircraftCarrier);
            if (finalResult == '~') {
                finalResult = result;
            }
        }
        if (battleship != null) {
            result = verifyShip(coordinateX, coordinateY, battleship);
            if (finalResult == '~') {
                finalResult = result;
            }
        }
        if (submarine != null) {
            result = verifyShip(coordinateX, coordinateY, submarine);
            if (finalResult == '~') {
                finalResult = result;
            }
        }
        if (cruiser != null) {
            result = verifyShip(coordinateX, coordinateY, cruiser);
            if (finalResult == '~') {
                finalResult = result;
            }
        }
        if (destroyer != null) {
            result = verifyShip(coordinateX, coordinateY, destroyer);
            if (finalResult == '~') {
                finalResult = result;
            }
        }

        return finalResult;

    }
}
