package com.sparta.simulator.model;

import java.util.ArrayList;
import java.util.Random;

public abstract class TrainingCentre {
    private final int MIN_CAPACITY = 25;
    private int currentCapacity = 0;
    private int onTheTraining = 0;

    public int getMIN_CAPACITY() { return MIN_CAPACITY; }

    public int getCurrentCapacity() {
        return currentCapacity;
    }
    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public int getOnTheTraining() { return onTheTraining; }
    public void setOnTheTraining(int onTheTraining) {
        this.onTheTraining = onTheTraining;
    }


    // number of trainees left
    public abstract int enrolledTraining(int num);

    public abstract void startNewTraining();
}
