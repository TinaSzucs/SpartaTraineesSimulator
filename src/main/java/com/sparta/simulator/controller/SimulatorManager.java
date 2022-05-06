package com.sparta.simulator.controller;

import com.sparta.simulator.model.*;
import com.sparta.simulator.start.display.Display;

import java.util.ArrayList;

public abstract class SimulatorManager {
    public static void initialise() {
        // storing list of TrainingCentres, Trainees currently on training, and Trainees on waiting list
        ArrayList<TrainingCentre> openCentres = new ArrayList<>();
        ArrayList<TrainingCentre> closedCentres = new ArrayList<>();
        ArrayList<Trainee> waitingTrainees = new ArrayList<>();
        ArrayList<Trainee> benchTrainees = new ArrayList<>();
        ArrayList<Client> clients = new ArrayList<>();
        ArrayList<Client> unhappyClient = new ArrayList<>();

        // we get the input from user to how long to run the simulation
        //      we assume the input is already checked, and will be a positive integer [ 1 - positive "infinite"]
        int runtime = Display.scanSimulationLength();

        // get input from user to set the minimum and maximum limit of clients to be generated each month after 1 year
        int minRange = Display.scanMinClientNumber();
        int maxRange = Display.scanMaxClientNumber(minRange);

        boolean monthlySummary = Display.scanChoiceOfSummary();


        // iterate through each tick [=month]
        for (int tick = 1; tick <= runtime; tick++) {
            System.out.printf("\n%d. month:\n", tick);

            // Every 2 months, Sparta Global opens training centres; they open instantly and can take trainees every month.
            if (tick % 2 == 1) {
                openTrainingCentre(openCentres);
            }

            // A centre can train a max of 100 trainees and takes a random number of trainees every month (0 - 50 trainees up to their capacity).
            monthlyTraineeIntake(openCentres);

            // If all centres are full, the trainees go onto a waiting list; this list must be served first before new trainees are taken.
            allocateFromWaitingList(waitingTrainees, openCentres);

            // Every month, a random number of trainees are generated, wanting to be trained (50 - 100).
            monthlyNewTrainees(waitingTrainees, openCentres);

            // If a centre has fewer than 25 trainees, it will close.
            // The trainees will be randomly moved to another suitable centre.
            // Bootcamp: can remain open for 3 months if there are fewer than 25 trainees in attendance. If a Bootcamp has 3 consecutive months of low attendance, it will close
            closeLowAttendanceCentres(openCentres, closedCentres, waitingTrainees);


            // If a trainee has been in training for 3 months, they are moved to a bench state.
            graduateTrainees(openCentres, benchTrainees);


            // Clients will begin to be randomly created after 1 year of the simulation.
            if (tick >= 12) {
                newClients(clients, minRange, maxRange);

                // A client will take a random number of trainees from the bench each month (1 - full requirement) until their requirement is met.
                // ISSUE HERE
                monthlyClientIntake(clients, benchTrainees);

                // If a client does not collect enough trainees from the bench within a year, they will leave unhappy.
                // If a client does collect enough trainees from the bench within a year, they will leave happy and return the next year with the same
                assessClientHappiness(clients, unhappyClient);
            }


            if (monthlySummary) {
                Display.monthlySummary(tick, openCentres, closedCentres, waitingTrainees);
            }
        }


        if ( !monthlySummary) {
            Display.summary(openCentres, closedCentres, waitingTrainees);
        }
    }



    private static void openTrainingCentre(ArrayList<TrainingCentre> centreList) {
        int hubCount =  0;
        int bootcampCount = 0;

        for (TrainingCentre centre: centreList) {
            if (centre instanceof TrainingHub) {
                hubCount++;
            } else if (centre instanceof Bootcamp) {
                bootcampCount++;
            }
        }

        TrainingCentre newCentre = TrainingCentreFactory.generateTrainingCentre(bootcampCount, hubCount);

        String centreType = String.valueOf(newCentre.getClass());
        centreType = centreType.substring(centreType.lastIndexOf('.')+1);
        System.out.printf("\t%S have been created.\n", centreType);

        centreList.add(newCentre);
    }


    private static void monthlyTraineeIntake(ArrayList<TrainingCentre> centreList){
        System.out.printf("\tcurrently we have %d open centres:\n", centreList.size());

        int index = 1;
        for(TrainingCentre centre: centreList) {
            int prevCapacity = centre.getCurrentCapacity();
            centre.startNewTraining();
            int newCapacity = centre.getCurrentCapacity();
            int newTrainingSize = newCapacity - prevCapacity;
            int freeSpace = centre.getFreeSpace();

            String centreType = String.valueOf(centre.getClass());
            centreType = centreType.substring(centreType.lastIndexOf('.')+1);

            if (centre instanceof TechCentre) {
                System.out.printf("\t\t%d. %S can take %d additional trainees this month. Now the centre's total capacity is %d, from which is %d free.\n", index, centreType, newTrainingSize, newCapacity, freeSpace);
            } else {
                System.out.printf("\t\t%d. %S can take %d additional trainees this month. Now the centre's total capacity is %d, from which is %d free.\n", index, centreType, newTrainingSize, newCapacity, freeSpace);
            }
            index++;
        }
    }


    private static ArrayList<Trainee> allocateTrainees(ArrayList<Trainee> traineesToAllocate, ArrayList<TrainingCentre> centreList) {
        // count how many total free spaces we have across the training centres
        int totalFreeSpace = 0;
        for (TrainingCentre centre: centreList) {
            totalFreeSpace += centre.getFreeSpace();
        }

        // if all centres are full, we return the given list without wasting time on executing the rest of the code
        if (totalFreeSpace == 0) {
            return traineesToAllocate;
        }

        // if we have more trainees than actual places, we just fill up all the spots without thinking
        // we iterate through the centres and add as many trainees as we can
        else if (traineesToAllocate.size() >= totalFreeSpace) {
            for (TrainingCentre centre : centreList) {
                if (centre.getFreeSpace() > 0) {
                    ArrayList<Trainee> leftoverTrainees = new ArrayList<>();
                    leftoverTrainees.addAll(centre.enrollTrainees(traineesToAllocate));

                    traineesToAllocate.clear();
                    traineesToAllocate.addAll(leftoverTrainees);
                }
            }
        }

        // if there are more spaces, than trainees to allocate
        // we iterate through the trainees, and insert them one by one to the next centre which still has space
        else {
            ArrayList<Trainee> couldNotAllocate = new ArrayList<>();
            int centreCount = centreList.size();
            int position = 0;
            boolean addedTrainee = false;

            // we will iterate through the trainees until there's no trainee left in the list
            // possible infinite loop thanks to TechCentre restriction? -> count attempts which will tell if we tried every centre in the list
            while (traineesToAllocate.size() > 0) {
                int attempts = 1;

                do {
                    addedTrainee = centreList.get(position%centreCount).enrollTrainee(traineesToAllocate.get(0));
                    position++;
                    attempts++;
                } while (addedTrainee && attempts < centreCount);

                if ( !addedTrainee ) {
                    couldNotAllocate.add(traineesToAllocate.get(0));
                }
                traineesToAllocate.remove(0);
            }

            traineesToAllocate = couldNotAllocate;
        }

        //  if we have trainees which we couldn't allocate due to space limit, it will remain in the original arraylist
        return traineesToAllocate;
    }


    private static void allocateFromWaitingList(ArrayList<Trainee> waitingList, ArrayList<TrainingCentre> centreList){
        System.out.printf("\tcurrently %d trainee(s) on the waiting list.\n", waitingList.size());

        // if there are elements in the waiting list, then we proceed to allocate them, else move on
        if (waitingList.size() > 0) {
            System.out.println("\t. . . allocating trainees . . .");
            allocateTrainees(waitingList, centreList);
            System.out.printf("\tafter allocating the waiting trainees among the free spaces, we have %d trainees on the waiting list.\n", waitingList.size());
        }

    }


    private static void monthlyNewTrainees(ArrayList<Trainee> waitingList, ArrayList<TrainingCentre> centreList){
        ArrayList<Trainee> newTrainees = TraineeFactory.generateTrainees();
        System.out.printf("\t%d new Trainees wanting to be trained this month.\n", newTrainees.size());

        // if we have a Trainee in the waiting list, this means all the spots are full -> just add the new Trainees at the end of the list
        // we will try to allocate the new trainees ONLY, in case the TechCentre has spot for trainees with matching Course type
        // [we assume all the waiting trainees are in different course]

//         maybe add a function to check if there's a Tech centre in the list??
//         if no, not to run allocate trainees
        if (waitingList.size() > 0) {
            allocateTrainees(newTrainees, centreList);

            waitingList.addAll(newTrainees);
            System.out.printf("\tafter allocating the new trainees among the course specific free spaces, we have %d trainees on the waiting list.\n", waitingList.size());
        }

        // if the waiting list is empty, we try to allocate the new trainees, and the leftover will be added to the waiting list
        else {
            waitingList.addAll(allocateTrainees(newTrainees, centreList));
            System.out.printf("\tafter allocating the new trainees among the free spaces, we have %d trainees on the waiting list.\n", waitingList.size());
        }
    }


    private static void closeLowAttendanceCentres(ArrayList<TrainingCentre> open, ArrayList<TrainingCentre> closed, ArrayList<Trainee> waitingList){
        System.out.println("\t. . . checking if a training centre needs to close . . .");
        // If a centre has fewer than 25 trainees, it will close.
        // Bootcamp: can remain open for 3 months if there are fewer than 25 trainees in attendance. If a Bootcamp has 3 consecutive months of low attendance, it will close.
        ArrayList<TrainingCentre> newOpenList = new ArrayList<>();
        ArrayList<Trainee> traineesToRelocate = new ArrayList<>();
        int index = 1;
        boolean closedAnyCentre = false;

        for (TrainingCentre centre: open) {
            if (centre.remainOpen()) {
                newOpenList.add(centre);
            } else {
                closedAnyCentre = true;
                traineesToRelocate.addAll(centre.getOnTheTraining());
                closed.add(centre);

                String centreType = String.valueOf(centre.getClass());
                centreType = centreType.substring(centreType.lastIndexOf('.')+1);

                System.out.printf("\t\t%d. %S training centre shuts down. It had only %d trainees.\n", index, centreType, centre.getOnTheTraining().size());
            }

            index++;
        }

        if (closedAnyCentre) {
            open = newOpenList;
            moveTraineesFromClosedCentre(traineesToRelocate, open, waitingList);
        } else {
            System.out.println("\tno centres was shut down.");
        }

    }


    private static void moveTraineesFromClosedCentre(ArrayList<Trainee> traineesToMove, ArrayList<TrainingCentre> centreList, ArrayList<Trainee> waitingList){
        System.out.println("\t. . . trying to relocate trainees from the closed centres . . .");
        int originalSize = traineesToMove.size();
        allocateTrainees(traineesToMove, centreList);
        int newSize = traineesToMove.size();
        System.out.printf("\t\t%d trainees were on training, from which we could relocate %d. The remaining %d added to the beginning of waiting list.\n", originalSize, originalSize-newSize, newSize);

        for (int i=traineesToMove.size()-1 ; i >= 0 ; i--) {
            waitingList.add(0, traineesToMove.get(i));
        }
    }


    private static void graduateTrainees(ArrayList<TrainingCentre> centreList, ArrayList<Trainee> benchList) {
        System.out.println("\t. . . graduating trainees . . . ");
        int oldBenchSize = benchList.size();
        int index = 1;

        // we iterate through all the training centres
        for (TrainingCentre centre: centreList) {
            ArrayList<Trainee> newTrainingList = new ArrayList<>();
            int graduateCount = 0;

            // we iterate through the trainees of each individual training centre to increase their time spent learning
            // if they reach the 3 months, they are graduated and instantly moved to the bench list
            for (Trainee trainee: centre.getOnTheTraining()) {
                if (trainee.addMonthTraining()) {
                    benchList.add(trainee);
                    graduateCount++;
                } else {
                    newTrainingList.add(trainee);
                }
            }

            if (graduateCount > 0) {
                String centreType = String.valueOf(centre.getClass());
                centreType = centreType.substring(centreType.lastIndexOf('.')+1);
                System.out.printf("\t\t%d. %S:\t%d trainees graduated.\n", index, centreType, graduateCount);
            }

            centre.setOnTheTraining(newTrainingList);
            index++;
        }

        System.out.printf("\tin total %d trainees graduated this month.", benchList.size()-oldBenchSize);
    }


    private static void newClients(ArrayList<Client> existingClientList, int minRange, int maxRange){
        existingClientList.addAll(ClientFactory.generateClient(minRange, maxRange));
    }


    private static void monthlyClientIntake(ArrayList<Client> clients, ArrayList<Trainee> benchList){
        if (benchList.size() > 0) {
            System.out.println("\t. . . allocating trainees from bench to clients . . .");
            int index = 1;
            for (Client client: clients) {
                int beforeSize = benchList.size();
                client.monthlyIntake();
                client.addTraineesToClient(benchList);
                System.out.printf("\t\t%d. client requirement is %d %S trainees. The client can take a maximum of %d trainees at the moment, from which %d spots still not filled.\n", index, client.getRequirementNumber(), client.getCourse(), client.getCurrentLimit(), client.getFreeSpace());
                index++;
            }
        }
    }


    private static void assessClientHappiness(ArrayList<Client> clientList, ArrayList<Client> unhappyClientList){
        ArrayList<Client> newClientList = new ArrayList<>();

        int index = 1;

        for (Client client: clientList) {
            if (client.tickClient()) {
                newClientList.add(client);
            } else {
                unhappyClientList.add(client);
                System.out.printf("\t%d. client left, as they are in short of %d trainees to meet their %d %S requirement.\n", index, client.getFreeSpace(), client.getRequirementNumber(), client.getCourse());
            }

            index++;
        }

        clientList = newClientList;
    }

}
