package com.sparta.simulator.model;

import com.sparta.simulator.logging.LogDriver;

import java.util.Random;

public class TrainingCentreFactory {
    public static TrainingCentre generateTrainingCentre(int bootcampCount, int trainingHubCount) {
        Random rand = new Random();
        TrainingCentre centre;
        int centreType;
        /*
            TrainingHub = 0     (limit up to 3)
            TechCentre  = 1
            Bootcamp    = 2     (limit up to 2)
         */

        // generate a random number restricted by the bootcamp & hub count
        if (bootcampCount >= 2 && trainingHubCount >= 3) {
            centreType = 1;
        } else if (bootcampCount >= 2) {
            centreType = rand.nextInt(0, 2);
        } else if (trainingHubCount >= 3) {
            centreType = rand.nextInt(1, 3);
        } else {
            centreType = rand.nextInt(0, 3);
        }

        // construct a Training Centre based on the acquired centre type
        switch (centreType) {
            case 0:
                centre = new TrainingHub();
                break;
            case 1:
                centre = new TechCentre();
                break;
            case 2:
                centre = new Bootcamp();
                break;
            default:
                centre = new TrainingCentre();
                LogDriver.errorLog("Could not generate a specialised Training Centre @TrainingCentreFactory generateTrainingCentre().");
        }

        return centre;
    }
}
