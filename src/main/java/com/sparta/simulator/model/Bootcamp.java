package com.sparta.simulator.model;

import java.util.ArrayList;
import java.util.Random;

public class Bootcamp extends TrainingCentre {
    private final int MIN_CAPACITY = 25;
    private final int MAX_CAPACITY = 500;
    private int currentCapacity;
    private ArrayList<Trainee> onTheTraining ;
    private int lowAttendanceTime;


    public Bootcamp() {
        this.currentCapacity = 0;
        this.onTheTraining = new ArrayList<>();
        this.lowAttendanceTime = 0;
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

    public void setLowAttendanceTime(int lowAttendanceTime) {
        this.lowAttendanceTime = lowAttendanceTime;
    }

    public int getLowAttendanceTime() {
        return lowAttendanceTime;
    }

    public void incrementLowAttendanceTime() {
        if (this.onTheTraining.size() < 25) {
            this.lowAttendanceTime++;
        }
    }


    // to reset the lowAttendanceTime, whenever new trainee added and the attendance reached 25!
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

        resetLowAttendanceTime();

        return leftoverTrainees;
    }

    @Override
    public boolean enrollTrainee(Trainee trainee) {
        boolean successful = false;

        if (this.getFreeSpace() > 0) {
            this.onTheTraining.add(trainee);
            successful = true;
        }

        resetLowAttendanceTime();

        return successful;
    }

    @Override
    public void startNewTraining(){
        Random rand = new Random();
        int max = this.MAX_CAPACITY - this.currentCapacity;
        int places;
        if(max >= 50) {
            places = rand.nextInt(0,51);
        } else {
            places = rand.nextInt(0, max+1);
        }
        this.currentCapacity += places;

        incrementLowAttendanceTime();
    }

    @Override
    public int getFreeSpace() {
        return this.getCurrentCapacity() - this.getOnTheTraining().size();
    }

    private void resetLowAttendanceTime() {
        if (this.onTheTraining.size() >= 25) {
            this.lowAttendanceTime = 0;
        }
    }

    @Override
    public boolean remainOpen() {
        return (onTheTraining.size() >= 25 || lowAttendanceTime < 3);
    }

}