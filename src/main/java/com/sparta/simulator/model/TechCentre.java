package com.sparta.simulator.model;

import com.sparta.simulator.logging.LogDriver;

import java.util.ArrayList;
import java.util.Random;

public class TechCentre extends TrainingCentre {
    private final int MIN_CAPACITY = 25;
    private final int MAX_CAPACITY = 200;
    private int currentCapacity = 0;
    private ArrayList<Trainee> onTheTraining;
    private Course course;


    public TechCentre() {
        this.currentCapacity = 0;
        this.onTheTraining = new ArrayList<Trainee>();
        Random random = new Random();
        this.course = Course.values()[random.nextInt(0, Course.values().length)];

        LogDriver.debugLog(String.format("%S have been created, with %S specialisation.", this.getCentreName(), this.getCourse()));
    }


    public int getMAX_CAPACITY() {
        return MAX_CAPACITY;
    }

    public Course getCourse() { return course; }

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


    // to check the Trainee course matches the Hub type before trying to enroll to the Trainee
    @Override
    public ArrayList<Trainee> enrollTrainees(ArrayList<Trainee> trainees) {
        ArrayList<Trainee> leftoverTrainees = new ArrayList<>();
        int freeSpaces = this.getFreeSpace();

        for (Trainee trainee: trainees) {
            if (freeSpaces == 0) {
                leftoverTrainees.add(trainee);
            } else if (enrollTrainee(trainee)) {
                freeSpaces--;
            } else {
                leftoverTrainees.add(trainee);
            }
        }

        return leftoverTrainees;
    }

    @Override
    public boolean enrollTrainee(Trainee trainee) {
        boolean successful = false;

        if ( this.getFreeSpace() > 0 && isCourseMatch(trainee) ) {
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

    public boolean isCourseMatch(Trainee trainee) {
        return (trainee.getCourseType() == this.course);
    }

    @Override
    public int getFreeSpace() {
        return this.getCurrentCapacity() - this.getOnTheTraining().size();
    }

    @Override
    public boolean remainOpen() {
        return (this.onTheTraining.size() >= 25);
    }

}
