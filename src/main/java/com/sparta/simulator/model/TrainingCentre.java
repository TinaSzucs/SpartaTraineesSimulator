package com.sparta.simulator.model;

import java.util.Random;

public class TrainingCentre {
    private final int CAPACITY = 100;
    private int currentCapacity = 0;
    private int onTheTraining = 0;


    public TrainingCentre() {
        this.currentCapacity = 0;
        startNewTraining();
        this.onTheTraining = 0;
    }

    public int getCAPACITY() {
        return CAPACITY;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public int getOnTheTraining() {
        return onTheTraining;
    }

    public void setOnTheTraining(int onTheTraining) {
        this.onTheTraining = onTheTraining;
    }

    public int enrolledTraining(int num) {
        int leftTrainees = 0;
        int freeSpaces = this.currentCapacity - this.onTheTraining;
        if(freeSpaces >= num) {
            this.onTheTraining += num;
        } else {
            leftTrainees = num - freeSpaces;
            this.onTheTraining += freeSpaces;
        }
        return leftTrainees;
    }

    public void startNewTraining() {
        Random rand = new Random();
        int max = CAPACITY - this.currentCapacity;
        int places;
        if(max >= 50) {
            places = rand.nextInt(0,51);
        } else {
            places = rand.nextInt(0, max+1);
        }
        this.currentCapacity += places;
    }
}
