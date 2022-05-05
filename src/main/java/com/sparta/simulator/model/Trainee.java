package com.sparta.simulator.model;

import java.util.Random;

public class Trainee {

    private Course courseType;
    private int monthsTrained;

    public Trainee()   {

        Random random = new Random();
        this.courseType = Course.values()[random.nextInt(0, Course.values().length)];
        this.monthsTrained = 0;
    }

    public Course getCourseType() {
        return courseType;
    }

    public void addMonthTraining()  {
        monthsTrained++;
    }
    public int getMonthsTrained() {
        return monthsTrained;
    }
}
