package com.sparta.simulator.model;

import java.util.Random;

public class TechCentre extends TrainingCentre {

    private final int MIN_CAPACITY = 25;
    private final int MAX_CAPACITY = 200;

    public Course getCourse() { return course; }

    private Course course;
    private int currentCapacity = 0;
    private int onTheTraining = 0;


    public TechCentre() {
        this.currentCapacity = 0;
        startNewTraining();
        this.onTheTraining = 0;
        Random random = new Random();
        this.course = Course.values()[random.nextInt(0, Course.values().length)];
    }

    @Override
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

    @Override
    public void startNewTraining() {
        Random rand = new Random();
        int max = MAX_CAPACITY - this.currentCapacity;
        int places;
        if(max >= 50) {
            places = rand.nextInt(0,51);
        } else {
            places = rand.nextInt(0, max+1);
        }
        this.currentCapacity += places;
    }




}
