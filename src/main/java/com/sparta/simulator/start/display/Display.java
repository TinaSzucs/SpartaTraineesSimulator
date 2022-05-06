package com.sparta.simulator.start.display;

import com.sparta.simulator.model.Trainee;
import com.sparta.simulator.model.TrainingCentre;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.sparta.simulator.logging.LogDriver.logger;

public abstract class Display {

    public static void summaryDisplay(ArrayList<TrainingCentre> openCentres, ArrayList<TrainingCentre> closedCentres, ArrayList<Trainee> waitingList) {
        System.out.println("\n\nSUMMARY AT THE END OF SIMULATION\n");

//        StringBuilder sb = new StringBuilder("Number of open training centres:\n");
        int openHub, openBootcamp, openTech;
        int closedHub, closedBootcamp, closedTech;
        int fullHub, fullBootcamp, fullTech;

        fullHub = fullBootcamp = fullTech = closedHub = closedBootcamp = closedTech = openHub = openBootcamp = openTech = 0;
        for (TrainingCentre open: openCentres) {
            openHub++;
            // code to add to corresponding centre type

//            if (open.getFreeSpace() == 0) {
//                fullHub++;
//            }
        }

        for (TrainingCentre close: closedCentres) {
            closedHub++;
        }



        System.out.println("Number of closed training centres: " );
        System.out.println("Number of full training centres: " );

        //Java, C#, Data, DevOps or Business
        int java, cSharp, data, devOps, business =0;

        System.out.println("Number of trainees in training: " );
        System.out.println("Number of trainees in waiting list: " );

    }

    public static int scanSimulationLength() {
        boolean suitableInput = false;
        int simulationLength = -1;
        System.out.println("\nSetting the simulation length...");

        while ( !suitableInput ) {
            try {
                Scanner scan = new Scanner(System.in);

                do {
                    System.out.println("Please enter a positive number, which represents the months:");

                    simulationLength = scan.nextInt();

                } while (simulationLength < 1);

                suitableInput = true;

            } catch (InputMismatchException e) {
//                e.printStackTrace();
                System.out.print("The input is not a number!! ");
                logger.warn("Invalid input");
            }
        }

        return simulationLength;
    }

    public static int scanChoiceOfSummary() {
        boolean suitableInput = false;
        int summaryChoice = 0;

        System.out.println("\nPlease select the type of output option:\n\t1. Summary data at the end of the simulation\n\t2. Running output updated each month.");

        while ( !suitableInput ) {
            try {
                Scanner scan = new Scanner(System.in);

                do {
                    System.out.println("Please enter the corresponding number");
                    summaryChoice = scan.nextInt();

                } while ( !(summaryChoice == 1 || summaryChoice == 2) );

                suitableInput = true;

            } catch (InputMismatchException e) {
//                e.printStackTrace();
                // maybe log the user gave incorrect input, starting the loop again
                System.out.print("The input is not a number!! ");
                logger.warn("Invalid input");
            }
        }

        return summaryChoice;
    }
}