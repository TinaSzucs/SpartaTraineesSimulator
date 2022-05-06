package com.sparta.simulator.model;

import java.util.ArrayList;
import java.util.Random;

public class Client {
    private int tickClient;
    private Course course;
    private int requirementNumber;
    private ArrayList<Trainee> trainees;
    private int currentLimit;

    public Client(Course course, int requirementNumber) {
        this.requirementNumber = requirementNumber;
        this.course = course;
        this.trainees = new ArrayList<>(requirementNumber);
        this.tickClient = 0;
        this.currentLimit = 0;
    }

    public Course getCourse() {return course;}

    public int getTickClient() {
        return tickClient;
    }

    public void setTickClient(int tickClient) {
        this.tickClient = tickClient;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getRequirementNumber() {
        return requirementNumber;
    }

    public void setRequirementNumber(int requirementNumber) {
        this.requirementNumber = requirementNumber;
    }

    public ArrayList<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(ArrayList<Trainee> trainees) {
        this.trainees = trainees;
    }

    public int getCurrentLimit() {
        return currentLimit;
    }

    public void setCurrentLimit(int currentLimit) {
        this.currentLimit = currentLimit;
    }

    public int getFreeSpace() {
        return (this.currentLimit - this.trainees.size());
    }


    public void addTraineesToClient(ArrayList<Trainee> traineesToAllocate) {
        int freeSpace = this.getFreeSpace();
        int position = 0;

        while (freeSpace > 0 && position < traineesToAllocate.size()) {
            Trainee trainee = traineesToAllocate.get(position);

            if (trainee.getCourseType() == this.getCourse()) {
                this.getTrainees().add(trainee);
                freeSpace--;
                traineesToAllocate.remove(position);
            } else {
                position++;
            }
        }

    }

    public boolean tickClient() {
        this.tickClient++;

        if (this.tickClient > 12) {

            if(this.trainees.size() >= this.requirementNumber) {
                this.trainees.clear();
                this.tickClient = 0;
                this.currentLimit = 0;

                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public void monthlyIntake() {
        if ( this.requirementNumber > this.currentLimit) {
            int max = this.requirementNumber - this.currentLimit;

            Random rand = new Random();
            int intake = rand.nextInt(1, max+1);
            this.currentLimit += intake;
        }
    }

}
