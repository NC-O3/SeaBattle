package com.company;

public class Main {

    public static void main(String[] args) {

        try {
            Field field = new Field();
            field.displayField();
            field.autoCompleter();
        } catch (InvalidCoordinatesException e) {
            System.out.println(e.getMessage());
        }

    }


}
