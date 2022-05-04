package com.sparta.simulator.model;

import java.util.Random;

public class Trainee {

    private Course courseType;

    public Trainee()   {

        Random random = new Random();
        this.courseType = Course.values()[random.nextInt(0, Course.values().length)];
    }
}
