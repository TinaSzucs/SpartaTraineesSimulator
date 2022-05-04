package com.sparta.simulator.model;

public enum Course {
    //list of courses available for trainees
    JAVA("Java"),
    C_SHARP("C#"),
    DATA("Data"),
    DEV_OPS("DevOps"),
    BUSINESS("Business");

    private String courseType;

    Course(String courseType)   {
        this.courseType = courseType;
    }

    public String getCourseType()   {
        return courseType;
    }

}
