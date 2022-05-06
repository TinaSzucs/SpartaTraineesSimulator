package com.sparta.simulator.model;

import com.sparta.simulator.logging.LogDriver;

import java.util.ArrayList;
import java.util.Random;

public class TrainingHub extends TrainingCentre {
    private final int MIN_CAPACITY = 25;
    private final int MAX_CAPACITY = 100;
    private int currentCapacity;
    private ArrayList<Trainee> onTheTraining;


    public TrainingHub() {
        this.currentCapacity = 0;
        this.onTheTraining = new ArrayList<>();

        LogDriver.info(String.format("%S have been created.", this.getCentreName()));
    }


    public int getMAX_CAPACITY() {
        return MAX_CAPACITY;
    }

    @Override
    public int getCurrentCapacity() {
        return currentCapacity;
    }

    @Override
    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    @Override
    public ArrayList<Trainee> getOnTheTraining() {
        return onTheTraining;
    }

    @Override
    public void setOnTheTraining(ArrayList<Trainee> onTheTraining) {
        this.onTheTraining = onTheTraining;
    }


    @Override
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

    @Override
    public boolean enrollTrainee(Trainee trainee) {
        boolean successful = false;

        if (this.getFreeSpace() > 0) {
            this.onTheTraining.add(trainee);
            successful = true;
        }

        return successful;
    }

    @Override
    public int startNewTraining(){
        Random rand = new Random();
        int max = this.MAX_CAPACITY - this.currentCapacity;
        int places;
        if(max >= 50) {
            places = rand.nextInt(0,51);
        } else {
            places = rand.nextInt(0, max+1);
        }
        this.currentCapacity += places;
        return places;
    }

    @Override
    public int getFreeSpace() {
        return this.getCurrentCapacity() - this.getOnTheTraining().size();
    }

    @Override
    public boolean remainOpen() {
        return (onTheTraining.size() >= 25);
    }
}

