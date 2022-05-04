package com.sparta.simulator.controller;

import com.sparta.simulator.model.TrainingCentre;
import com.sparta.simulator.start.display.Display;
import com.sparta.simulator.model.Trainee;
import com.sparta.simulator.model.TraineeFactory;

import java.util.ArrayList;

public abstract class SimulatorManager {
    public static void initialise() {
        // storing list of TrainingCentres, Trainees currently on training, and Trainees on waiting list
        ArrayList<TrainingCentre> centreList = new ArrayList<>();
        ArrayList<Trainee> waitingTrainees = new ArrayList<>();

        // we get the input from user to how long to run the simulation
        //      we assume the input is already checked, and will be a positive integer [ 1 - positive "infinite"]
        int runtime = Display.mainDisplay();


        // how many centres to generate each 2 month
        // right now its a set value
        // can be changed later to be based on user input
        int centresToGenerate = 1;


        // iterate through each tick [=month]
        for (int i=0 ; i <= runtime ; i++) {
            System.out.printf("\n%d. month:\n", i);

            // Every 2 months, Sparta Global opens training centres; they open instantly and can take trainees every month.
            // will handle the open instantly and can take trainees part when iterating through every centre together
            if (i%2 == 0) {
                System.out.println("\t1 new centre have been created.");

                TrainingCentre newCentre = new TrainingCentre();
                centreList.add(newCentre);
            }

            // A centre can train a max of 100 trainees and takes a random number of trainees every month (0 - 50 trainees up to their capacity).
            System.out.printf("\tcurrently we have %d centres:\n", centreList.size());
            int totalFreeSpace = 0;

            int index = 1;
            for(TrainingCentre centre: centreList) {
                int prevCapacity = centre.getCurrentCapacity();
                centre.startNewTraining();
                int newCapacity = centre.getCurrentCapacity();
                int newTrainingSize = newCapacity - prevCapacity;
                int freeSpace = centre.getFreeSpace();

                System.out.printf("\t\t%d. centre can take %d additional trainees this month. Now the centre's total capacity is %d, from which is %d free.\n", index, newTrainingSize, newCapacity, freeSpace);
                totalFreeSpace += freeSpace;
                index++;
            }

            // if we have any trainee on the waiting list, we try to allocate them first
            System.out.printf("\tcurrently %d trainee(s) on the waiting list.\n", waitingTrainees.size());
            if (waitingTrainees.size() > 0) {
                System.out.println("\t. . . allocating trainees . . .");
                ArrayList<Trainee> newWaitingList = new ArrayList<>();
                newWaitingList.addAll(allocateTrainees(waitingTrainees, centreList));
                System.out.printf("\tafter allocating the waiting trainees among the free spaces, we have %d trainees on the waiting list.\n", waitingTrainees.size());
            }

            // call function to generate new Trainees wanting to be trained 50-100
            ArrayList<Trainee> newTrainees = TraineeFactory.generateTrainees();
            System.out.printf("\t%d new Trainees wanting to be trained this month.\n", newTrainees.size());

            // if we have a Trainee in the waiting list, this means all the spots are full -> just add the new Trainees at the end of the list
            // if the waiting list is empty, we try to allocate the new trainees, and the leftover will be added to the waiting list
            if (waitingTrainees.size() > 0) {
                waitingTrainees.addAll(newTrainees);
                System.out.printf("\tafter adding the new trainees to the waiting list, we have %d trainees on the waiting list.\n", waitingTrainees.size());
            } else {
                waitingTrainees.addAll(allocateTrainees(newTrainees, centreList));
                System.out.printf("\tafter allocating the new trainees among the free spaces, we have %d trainees on the waiting list.\n", waitingTrainees.size());
            }

            // call function from Display to print out that month's infos:
//                number of open centres
//                number of full centres
//                number of trainees currently training
//                number of trainees on the waiting list
        }

        // call function from Display to print out the FINAL results [once the x month is over]

    }


    private static ArrayList<Trainee> allocateTrainees(ArrayList<Trainee> traineesToAllocate, ArrayList<TrainingCentre> centreList) {
        // count how many total free spaces we have across the training centres
        int totalFreeSpace = 0;
        for (TrainingCentre centre: centreList) {
            totalFreeSpace += centre.getFreeSpace();
        }

        // if we have more trainees than actual places, we just fill up all the spots without thinking
        // we iterate through the centres and add as many trainees as we can
        if (totalFreeSpace == 0) {
            return traineesToAllocate;
        } else if (traineesToAllocate.size() >= totalFreeSpace){
            for (TrainingCentre centre: centreList) {
                if (centre.getFreeSpace() > 0) {
                    ArrayList<Trainee> leftoverTrainees = new ArrayList<>();
                    leftoverTrainees.addAll(centre.enrollTrainees(traineesToAllocate));
                    traineesToAllocate.clear();
                    traineesToAllocate.addAll(leftoverTrainees);
                }
            }

        // we have more spaces than trainees to allocate
        // we iterate through the trainees, and insert them one by one to the next centre which still has space

        } else {
            int centreCount = centreList.size();
            int position = 0;
            boolean addedTrainee = false;

            // we will iterate through the trainees until there's no trainee left in the list
            while (traineesToAllocate.size() > 0) {
                do {
                    addedTrainee = centreList.get(position%centreCount).enrollTrainee(traineesToAllocate.get(0));
                    position++;
                } while (addedTrainee);

                traineesToAllocate.remove(0);
            }
        }

        //  if we have trainees which we couldn't allocate due to space limit, it will remain in the original arraylist
        return traineesToAllocate;
    }

//    private static void

}
