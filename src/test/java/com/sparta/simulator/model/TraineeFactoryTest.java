package com.sparta.simulator.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TraineeFactoryTest {
    Trainee trainee = new Trainee();

    @Test
    void generateTraineesLength() {
        ArrayList<Trainee> newTrainees = TraineeFactory.generateTrainees();
        assertTrue(newTrainees.size() >= 50 && newTrainees.size() <= 100);
    }

    @Test
    void enrolTrainees() {
    }

    @Test
    void benchTrainees() {
    }

    @Test
    void getBenchList() {
    }
}