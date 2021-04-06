package com.company;

public abstract class Ship {

    private int headX;
    private int headY;
    private int tailX;
    private int tailY;
    private int size;
    private int[] health;
    private String name;

    public Ship(int headX, int headY, int tailX, int tailY, int size, String name) {
        this.headX = headX;
        this.headY = headY;
        this.tailX = tailX;
        this.tailY = tailY;
        this.size = size;
        this.name = name;
        health = new int[size];
        completeHealth();
    }

    private void completeHealth() {
        for (int i = 0; i < size; i++) {
            health[i] = 1;
        }
    }

    protected void hit(int hitPoint) {
        health[hitPoint] = 0;
    }

    public String getName() {
        return name;
    }

    public int[] getHealth() {
        return health;
    }

    public int getHeadX() {
        return headX;
    }

    public int getHeadY() {
        return headY;
    }

    public int getTailX() {
        return tailX;
    }

    public int getTailY() {
        return tailY;
    }

    public int getSize() {
        return size;
    }
}
