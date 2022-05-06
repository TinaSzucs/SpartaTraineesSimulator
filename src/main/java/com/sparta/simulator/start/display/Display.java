package com.sparta.simulator.start.display;

import com.sparta.simulator.model.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Display {
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
                System.out.print("The input is not a number!! ");
            }
        }

        return simulationLength;
    }

    public static int scanMinClientNumber() {
        boolean suitableInput = false;
        int minRange = -1;
        System.out.println("\nSetting the client minimum range...");

        while ( !suitableInput ) {
            try {
                Scanner scan = new Scanner(System.in);

                do {
                    System.out.println("Please enter a non-negative number. This number represents the minimum amount of clients appearing monthly after the first year:");

                    minRange = scan.nextInt();

                } while (minRange < 0);

                suitableInput = true;

            } catch (InputMismatchException e) {
                System.out.print("The input is not a number!! ");
            }
        }

        return minRange;

    }

    public static int scanMaxClientNumber(int minRange) {
        boolean suitableInput = false;
        int maxRange = -1;
        System.out.println("\nSetting the client maximum range...");

        while ( !suitableInput ) {
            try {
                Scanner scan = new Scanner(System.in);

                do {
                    System.out.println("Please enter a positive number, which is greater than or equal to %d. This number represents maximum amount of clients appearing monthly after the first year:");

                    maxRange = scan.nextInt();

                } while (maxRange < minRange);

                suitableInput = true;

            } catch (InputMismatchException e) {
                System.out.print("The input is not a number!! ");
            }
        }

        return maxRange;

    }

    public static boolean scanChoiceOfSummary() {
        boolean suitableInput = false;
        int summaryChoice = 0;
        System.out.println("\nSetting the simulation output...");

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
                System.out.print("The input is not a number!! ");
            }
        }

        return (summaryChoice == 2);
    }

    public static void monthlySummary(int tick, ArrayList<TrainingCentre> open, ArrayList<TrainingCentre> closed, ArrayList<Trainee> waiting) {
        System.out.printf("\n\n\n%d. MONTH'S SUMMARY:\n", tick);
        summary(open, closed, waiting);
    }

    public static void summary(ArrayList<TrainingCentre> open, ArrayList<TrainingCentre> closed, ArrayList<Trainee> waiting) {
        int openTech = 0;
        int openHub = 0;
        int openBootcamp = 0;
        int fullTech = 0;
        int fullHub = 0;
        int fullBootcamp = 0;
        ArrayList<Trainee> training = new ArrayList<>();

        for (TrainingCentre centre: open) {
            if (centre instanceof TechCentre) {
                openTech++;
                if (centre.getCurrentCapacity() == centre.getOnTheTraining().size()) {
                    fullTech++;
                }
            } else if (centre instanceof TrainingHub) {
                openHub++;
                if (centre.getCurrentCapacity() == centre.getOnTheTraining().size()) {
                    fullHub++;
                }
            } else {
                openBootcamp++;
                if (centre.getCurrentCapacity() == centre.getOnTheTraining().size()) {
                    fullBootcamp++;
                }
            }

            training.addAll(centre.getOnTheTraining());
        }

        int closedTech = 0;
        int closedHub = 0;
        int closedBootcamp = 0;
        for (TrainingCentre centre: closed) {
            if (centre instanceof TechCentre) {
                closedTech++;
            } else if (centre instanceof TrainingHub) {
                closedHub++;
            } else {
                closedBootcamp++;
            }
        }

        int[] trainingCourseSummary = getTraineesCourseType(training);
        int[] waitingCourseSummary = getTraineesCourseType(waiting);


        StringBuilder sb = new StringBuilder();
        sb.append("\nNUMBER OF OPEN CENTRES:\t");
        sb.append("\tTraining Hub: ");
        sb.append(openHub);
        sb.append("\n\t\t\t\t\t\t\tBootcamp: ");
        sb.append(openBootcamp);
        sb.append("\n\t\t\t\t\t\t\tTech Centre: ");
        sb.append(openTech);

        sb.append("\n\nNUMBER OF CLOSED CENTRES:\t");
        sb.append("Training Hub: ");
        sb.append(closedHub);
        sb.append("\n\t\t\t\t\t\t\tBootcamp: ");
        sb.append(closedBootcamp);
        sb.append("\n\t\t\t\t\t\t\tTech Centre: ");
        sb.append(closedTech);

        sb.append("\n\nNUMBER OF FULL CENTRES:\t");
        sb.append("\tTraining Hub: ");
        sb.append(fullHub);
        sb.append("\n\t\t\t\t\t\t\tBootcamp: ");
        sb.append(fullBootcamp);
        sb.append("\n\t\t\t\t\t\t\tTech Centre: ");
        sb.append(fullTech);

                /*
            trainees[0]     JAVA("Java"),
            trainees[1]     C_SHARP("C#"),
            trainees[2]     DATA("Data"),
            trainees[3]     DEV_OPS("DevOps"),
            trainees[4]     BUSINESS("Business");
            trainees[5]     default, something went wrong
         */

        sb.append("\n\nNUMBER OF TRAINEES CURRENTLY TRAINING:\t");
        sb.append("Java: ");
        sb.append(trainingCourseSummary[0]);
        sb.append("\n\t\t\t\t\t\t\t\t\t\tC#: ");
        sb.append(trainingCourseSummary[1]);
        sb.append("\n\t\t\t\t\t\t\t\t\t\tData: ");
        sb.append(trainingCourseSummary[2]);
        sb.append("\n\t\t\t\t\t\t\t\t\t\tDevOps: ");
        sb.append(trainingCourseSummary[3]);
        sb.append("\n\t\t\t\t\t\t\t\t\t\tBusiness: ");
        sb.append(trainingCourseSummary[4]);
        if (trainingCourseSummary[5] != 0) {
            sb.append("\n\t\t\t\t\t\t\t\t\t\tCould not retrieve course type: ");
            sb.append(trainingCourseSummary[5]);
        }

        sb.append("\n\nNUMBER OF TRAINEES CURRENTLY WAITING:\t");
        sb.append("Java: ");
        sb.append(waitingCourseSummary[0]);
        sb.append("\n\t\t\t\t\t\t\t\t\t\tC#: ");
        sb.append(waitingCourseSummary[1]);
        sb.append("\n\t\t\t\t\t\t\t\t\t\tData: ");
        sb.append(waitingCourseSummary[2]);
        sb.append("\n\t\t\t\t\t\t\t\t\t\tDevOps: ");
        sb.append(waitingCourseSummary[3]);
        sb.append("\n\t\t\t\t\t\t\t\t\t\tBusiness: ");
        sb.append(waitingCourseSummary[4]);
        if (waitingCourseSummary[5] != 0) {
            sb.append("\n\t\t\t\t\t\t\t\t\t\tCould not retrieve course type: ");
            sb.append(waitingCourseSummary[5]);
        }

        System.out.println(sb);
    }

    private static int[] getTraineesCourseType(ArrayList<Trainee> list) {
        /*
            trainees[0]     JAVA("Java"),
            trainees[1]     C_SHARP("C#"),
            trainees[2]     DATA("Data"),
            trainees[3]     DEV_OPS("DevOps"),
            trainees[4]     BUSINESS("Business");
            trainees[5]     default, something went wrong
         */
        int[] trainees = new int[]{0, 0, 0, 0, 0, 0};

        for (Trainee trainee: list) {
            Course course = trainee.getCourseType();
            switch (course) {
                case JAVA -> trainees[0]++;
                case C_SHARP -> trainees[1]++;
                case DATA -> trainees[2]++;
                case DEV_OPS -> trainees[3]++;
                case BUSINESS -> trainees[4]++;
                default -> trainees[5]++;
            }
        }

        return trainees;
    }
}