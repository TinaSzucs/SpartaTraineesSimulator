package com.sparta.simulator.model;

import java.util.Random;

public class Trainee {
    private int courseLength;
    private Course courseType;
    private int monthsTrained;

    public Trainee()   {

        Random random = new Random();
        this.courseType = Course.values()[random.nextInt(0, Course.values().length)];
        this.monthsTrained = 0;
        this.courseLength = 3;
    }

    public Course getCourseType() {
        return courseType;
    }

    public boolean addMonthTraining()  {
        monthsTrained++;

        return (monthsTrained >= 3);
    }
    public int getMonthsTrained() {
        return monthsTrained;
    }
}
