package com.sparta.simulator.start.display;

import com.sparta.simulator.logging.LogDriver;
import com.sparta.simulator.model.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Display {
    public static int scanSimulationLength() {
        LogDriver.traceLog("Asking for input from user for the length of simulation.");

        boolean suitableInput = false;
        int simulationLength = -1;

        System.out.println("\nSetting the simulation length...");

        while ( !suitableInput ) {
            try {
                Scanner scan = new Scanner(System.in);

                do {
                    System.out.println("Please enter a positive number, which represents the months:");

                    simulationLength = scan.nextInt();
                    LogDriver.debugLog(String.format("The users input is: %d.", simulationLength));

                } while (simulationLength < 1);

                suitableInput = true;
                LogDriver.debugLog(String.format("The %d input have been accepted.", simulationLength));

            } catch (InputMismatchException e) {
                System.out.print("The input is not a number!! ");
                LogDriver.errorLog("User gave a Not A Number input @Display scanSimulationLength(). Restarting the scanning for new input.");
            }
        }

        return simulationLength;
    }

    public static int scanMinClientNumber() {
        LogDriver.traceLog("Asking for input from user for the minimum limit for Clients to generate.");
        boolean suitableInput = false;
        int minRange = -1;

        System.out.println("\nSetting the client minimum range...");

        while ( !suitableInput ) {
            try {
                Scanner scan = new Scanner(System.in);

                do {
                    System.out.println("Please enter a non-negative number. This number represents the minimum amount of clients appearing monthly after the first year:");

                    minRange = scan.nextInt();
                    LogDriver.debugLog(String.format("The users input is: %d.", minRange));

                } while (minRange < 0);

                suitableInput = true;
                LogDriver.debugLog(String.format("The %d input have been accepted.", minRange));

            } catch (InputMismatchException e) {
                System.out.print("The input is not a number!! ");
                LogDriver.errorLog("User gave a Not A Number input @Display scanMinClientNumber(). Restarting the scanning for new input.");
            }
        }

        return minRange;
    }

    public static int scanMaxClientNumber(int minRange) {
        LogDriver.traceLog(String.format("Asking for input from user for the maximum limit for Clients to generate. The provided minimum range is: %d.", minRange));

        boolean suitableInput = false;
        int maxRange = -1;

        System.out.println("\nSetting the client maximum range...");

        while ( !suitableInput ) {
            try {
                Scanner scan = new Scanner(System.in);

                do {
                    System.out.printf("Please enter a positive number, which is greater than or equal to %d. This number represents maximum amount of clients appearing monthly after the first year:\n", minRange);

                    maxRange = scan.nextInt();
                    LogDriver.debugLog(String.format("The users input is: %d.", maxRange));

                } while (maxRange < minRange);

                suitableInput = true;
                LogDriver.debugLog(String.format("The %d input have been accepted.", maxRange));

            } catch (InputMismatchException e) {
                System.out.print("The input is not a number!! ");
                LogDriver.errorLog("User gave a Not A Number input @Display scanMaxClientNumber(). Restarting the scanning for new input.");
            }
        }

        return maxRange;
    }

    public static boolean scanChoiceOfSummary() {
        LogDriver.traceLog("Asking for input from user for the output type.");
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
                    LogDriver.debugLog(String.format("The users input is: %d.", summaryChoice));

                } while ( !(summaryChoice == 1 || summaryChoice == 2) );

                suitableInput = true;
                LogDriver.debugLog(String.format("The %d input have been accepted.", summaryChoice));
                LogDriver.errorLog("User gave a Not A Number input @Display scanChoiceOfSummary(). Restarting the scanning for new input.");

            } catch (InputMismatchException e) {
                System.out.print("The input is not a number!! ");

            }
        }

        LogDriver.traceLog(String.format("The provided input is %d. Returning value: %s.", summaryChoice, (summaryChoice==2)));
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