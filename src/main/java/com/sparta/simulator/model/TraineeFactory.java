package com.sparta.simulator.model;

import com.sparta.simulator.model.Trainee;
import java.util.ArrayList;
import java.util.Random;

public class TraineeFactory {

    private static Random random = new Random();
    private static int totalTrainees = 0;

    private static ArrayList<Trainee> allTrainees = new ArrayList<>();
    private static ArrayList<Trainee> waitingList = new ArrayList<>();
    private static ArrayList<Trainee> benchList = new ArrayList<>();

    public static ArrayList<Trainee> generateTrainees() {

        //generate a random number between 50 and 100 for the amount of trainees to be added
        int amountOfTrainees = random.nextInt(50,101);

        ArrayList<Trainee> newTrainees = new ArrayList<>();

        //create and add trainees to the list of new trainees
        for (int i = 0; i < amountOfTrainees; i++) {
            newTrainees.add(new Trainee());
        }

        //return the list of newly generated employees
        return newTrainees;
    }

    public static void enrolTrainees(ArrayList<Trainee> newTrainees) {
        allTrainees.addAll(newTrainees);

        waitingList.addAll(newTrainees);
        totalTrainees += newTrainees.size();
    }

    public static void benchTrainees(ArrayList<TrainingCentre> trainingCentres) {
        ArrayList<Trainee> removeFromCentre = new ArrayList<>();

        for (TrainingCentre trainingCentre : trainingCentres)   {

            for (Trainee trainee : trainingCentre.getOnTheTraining())   {
                //add another month trained for the trainee
                trainee.addMonthTraining();

                //once trained for 3 months the trainee is added to bench list
                if (trainee.getMonthsTrained() == 3)    {
                    benchList.add(trainee);
                    removeFromCentre.add(trainee);
                }
            }

            // remove the fully trained employees from the centres
            //trainingCentre.removeTrainees(removeFromCentre);
            removeFromCentre.clear();
        }
    }
}
