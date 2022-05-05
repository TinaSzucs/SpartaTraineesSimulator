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
        this.trainees = new ArrayList<>();
        this.tickClient = 0;
        this.currentLimit = 0;
    }

    public Course getCourse() {return course;}

    public int getTickClient() {return tickClient;}

    public ArrayList<Trainee> getTrainees() {return trainees;}
    public void setTrainees(ArrayList<Trainee> trainees) {this.trainees = trainees;}

    public int getRequirementNumber() {return requirementNumber;}
    public void setRequirementNumber(int requirementNumber) {this.requirementNumber = requirementNumber;}


    public ArrayList<Trainee> addTraineesToClient(ArrayList<Trainee> traineesToAllocate) {
        ArrayList<Trainee> notHiredTrainees = new ArrayList<>(traineesToAllocate);
        int freeSpace = currentLimit - trainees.size();
        for( int i = 0; i < traineesToAllocate.size(); i++) {
            if (freeSpace > 0 && traineesToAllocate.get(i).getCourseType() == this.course) {
                this.trainees.add(traineesToAllocate.get(i));
                freeSpace--;
            }
             else {
                 notHiredTrainees.add(traineesToAllocate.get(i));
            }
        }
        return notHiredTrainees;
    }

    public boolean tickClient() {
        this.tickClient++;
        if (tickClient > 12) {
            if(this.trainees.size() >= requirementNumber) {
                trainees.clear();
                tickClient = 0;
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public void monthlyIntake() {
        Random rand = new Random();
        int max = requirementNumber - trainees.size();
        int intake = rand.nextInt(1, max+1);
        currentLimit += intake;
    }

}
