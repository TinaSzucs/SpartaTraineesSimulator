package com.sparta.simulator.model;

import java.util.ArrayList;
import java.util.Random;

public class TrainingCentre {
    private final int CAPACITY = 100;
    private int currentCapacity;
    private ArrayList<Trainee> onTheTraining = new ArrayList<>();


    public TrainingCentre() {
        this.currentCapacity = 0;
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

    public ArrayList<Trainee> getOnTheTraining() {
        return onTheTraining;
    }

    public void setOnTheTraining(ArrayList<Trainee> onTheTraining) {
        this.onTheTraining = onTheTraining;
    }

    public int getFreeSpace() {
        return this.currentCapacity - this.onTheTraining.size();
    }

    public ArrayList<Trainee> enrollTrainees(ArrayList<Trainee> trainees) {
        ArrayList<Trainee> leftoverTrainees = new ArrayList<>();
        int freeSpaces = this.getFreeSpace();

        if(freeSpaces >= trainees.size()) {
            this.onTheTraining.addAll(trainees);
        } else {
            for (int i=0 ; i<freeSpaces ; i++) {
                this.onTheTraining.add(trainees.get(0));
                trainees.remove(0);
            }

            leftoverTrainees.addAll(trainees);
        }
        return leftoverTrainees;
    }

    public boolean enrollTrainee(Trainee trainee) {
        boolean successful = false;

        if (this.getFreeSpace() > 0) {
            this.onTheTraining.add(trainee);
            successful = true;
        }

        return successful;
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
