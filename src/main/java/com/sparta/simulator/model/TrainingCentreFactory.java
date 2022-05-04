package com.sparta.simulator.model;

import java.util.Random;

public class TrainingCentreFactory {
    public static TrainingCentre generateTrainingCentre(int bootcampCount, int trainingHubCount) {
        Random rand = new Random();
        TrainingCentre centre;
        // training hub limit = 3;
        // bootcamp limit = 2;
        if (bootcampCount == 2 && trainingHubCount == 3) {
            centre = new TechCentre();
        } else if (bootcampCount == 2) {
            int num = rand.nextInt(1,3);
            if(num == 1) {
                centre = new TechCentre();
            } else {
                centre = new TrainingHub();
            }
        } else if (trainingHubCount == 3) {
            int num = rand.nextInt(1,3);
            if(num == 1) {
                centre = new TechCentre();
            } else {
                centre = new Bootcamp();
            }
        } else {
            int num = rand.nextInt(1,4);
            if(num == 1) {
                centre = new TechCentre();
            } else if (num == 2){
                centre = new Bootcamp();
            } else {
                centre = new TrainingHub();
            }
        }
        return centre;
    }
}
