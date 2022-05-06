package com.sparta.simulator.start.display;

import com.sparta.simulator.logging.LogDriver;
import com.sparta.simulator.model.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Display {
    public static int scanSimulationLength() {
        LogDriver.debug("Asking for input from user for the length of simulation.");

        boolean suitableInput = false;
        int simulationLength = -1;

        System.out.println("\nSetting the simulation length...");

        while ( !suitableInput ) {
            try {
                Scanner scan = new Scanner(System.in);

                do {
                    System.out.println("Please enter a positive number, which represents the months:");

                    simulationLength = scan.nextInt();
                    LogDriver.info(String.format("The users input is: %d.", simulationLength));

                } while (simulationLength < 1);

                suitableInput = true;
                LogDriver.info(String.format("The %d input have been accepted.", simulationLength));

            } catch (InputMismatchException e) {
                System.out.print("The input is not a number!! ");
                LogDriver.errorLog("User gave a Not A Number input @Display scanSimulationLength(). Restarting the scanning for new input.");
            }
        }

        return simulationLength;
    }

    public static void noClientScan(int simulationLength) {
        System.out.printf("\nNo clients will generated in the simulation because the simulation length is set to %2d.\nIf you wish to have clients, make sure you set a number higher or equal to 12.\n\n", simulationLength);
    }

    public static int scanMinClientNumber() {
        LogDriver.debug("Asking for input from user for the minimum limit for Clients to generate.");
        boolean suitableInput = false;
        int minRange = -1;

        System.out.println("\nSetting the client minimum range...");

        while ( !suitableInput ) {
            try {
                Scanner scan = new Scanner(System.in);

                do {
                    System.out.println("Please enter a non-negative number. This number represents the minimum amount of clients appearing monthly after the first year:");

                    minRange = scan.nextInt();
                    LogDriver.info(String.format("The users input is: %d.", minRange));

                } while (minRange < 0);

                suitableInput = true;
                LogDriver.info(String.format("The %d input have been accepted.", minRange));

            } catch (InputMismatchException e) {
                System.out.print("The input is not a number!! ");
                LogDriver.errorLog("User gave a Not A Number input @Display scanMinClientNumber(). Restarting the scanning for new input.");
            }
        }

        return minRange;
    }

    public static int scanMaxClientNumber(int minRange) {
        LogDriver.debug(String.format("Asking for input from user for the maximum limit for Clients to generate. The provided minimum range is: %d.", minRange));

        boolean suitableInput = false;
        int maxRange = -1;

        System.out.println("\nSetting the client maximum range...");

        while ( !suitableInput ) {
            try {
                Scanner scan = new Scanner(System.in);

                do {
                    System.out.printf("Please enter a positive number, which is greater than or equal to %d. This number represents maximum amount of clients appearing monthly after the first year:\n", minRange);

                    maxRange = scan.nextInt();
                    LogDriver.info(String.format("The users input is: %d.", maxRange));

                } while (maxRange < minRange);

                suitableInput = true;
                LogDriver.info(String.format("The %d input have been accepted.", maxRange));

            } catch (InputMismatchException e) {
                System.out.print("The input is not a number!! ");
                LogDriver.errorLog("User gave a Not A Number input @Display scanMaxClientNumber(). Restarting the scanning for new input.");
            }
        }

        return maxRange;
    }

    public static boolean scanChoiceOfSummary() {
        LogDriver.debug("Asking for input from user for the output type.");
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
                    LogDriver.info(String.format("The users input is: %d.", summaryChoice));

                } while ( !(summaryChoice == 1 || summaryChoice == 2) );

                suitableInput = true;
                LogDriver.info(String.format("The %d input have been accepted.", summaryChoice));


            } catch (InputMismatchException e) {
                System.out.print("The input is not a number!! ");
                LogDriver.errorLog("User gave a Not A Number input @Display scanChoiceOfSummary(). Restarting the scanning for new input.");
            }
        }

        LogDriver.debug(String.format("The provided input is %d. Returning value: %s.", summaryChoice, (summaryChoice==2)));
        return (summaryChoice == 2);
    }

    public static void monthlySummary(int tick, ArrayList<TrainingCentre> open, ArrayList<TrainingCentre> closed, ArrayList<Trainee> waiting, ArrayList<Trainee> bench) {
        System.out.printf("\n\n%d. MONTH'S SUMMARY:\n\n", tick);
        summary(open, closed, waiting, bench);
    }

    public static void summary(ArrayList<TrainingCentre> open, ArrayList<TrainingCentre> closed, ArrayList<Trainee> waiting, ArrayList<Trainee> bench) {
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
        int[] benchCourseSummary = getTraineesCourseType(bench);
//
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("\nNUMBER OF OPEN CENTRES:\t");
//        sb.append("\tTraining Hub: ");
//        sb.append(openHub);
//        sb.append("\n\t\t\t\t\t\t\tBootcamp: ");
//        sb.append(openBootcamp);
//        sb.append("\n\t\t\t\t\t\t\tTech Centre: ");
//        sb.append(openTech);
//
//        sb.append("\n\nNUMBER OF CLOSED CENTRES:\t");
//        sb.append("Training Hub: ");
//        sb.append(closedHub);
//        sb.append("\n\t\t\t\t\t\t\tBootcamp: ");
//        sb.append(closedBootcamp);
//        sb.append("\n\t\t\t\t\t\t\tTech Centre: ");
//        sb.append(closedTech);
//
//        sb.append("\n\nNUMBER OF FULL CENTRES:\t");
//        sb.append("\tTraining Hub: ");
//        sb.append(fullHub);
//        sb.append("\n\t\t\t\t\t\t\tBootcamp: ");
//        sb.append(fullBootcamp);
//        sb.append("\n\t\t\t\t\t\t\tTech Centre: ");
//        sb.append(fullTech);
//
//                /*
//            trainees[0]     JAVA("Java"),
//            trainees[1]     C_SHARP("C#"),
//            trainees[2]     DATA("Data"),
//            trainees[3]     DEV_OPS("DevOps"),
//            trainees[4]     BUSINESS("Business");
//            trainees[5]     default, something went wrong
//         */
//
//        sb.append("\n\nNUMBER OF TRAINEES CURRENTLY TRAINING:\t");
//        sb.append("Java: ");
//        sb.append(trainingCourseSummary[0]);
//        sb.append("\n\t\t\t\t\t\t\t\t\t\tC#: ");
//        sb.append(trainingCourseSummary[1]);
//        sb.append("\n\t\t\t\t\t\t\t\t\t\tData: ");
//        sb.append(trainingCourseSummary[2]);
//        sb.append("\n\t\t\t\t\t\t\t\t\t\tDevOps: ");
//        sb.append(trainingCourseSummary[3]);
//        sb.append("\n\t\t\t\t\t\t\t\t\t\tBusiness: ");
//        sb.append(trainingCourseSummary[4]);
//        if (trainingCourseSummary[5] != 0) {
//            sb.append("\n\t\t\t\t\t\t\t\t\t\tCould not retrieve course type: ");
//            sb.append(trainingCourseSummary[5]);
//        }
//
//        sb.append("\n\nNUMBER OF TRAINEES CURRENTLY WAITING:\t");
//        sb.append("Java: ");
//        sb.append(waitingCourseSummary[0]);
//        sb.append("\n\t\t\t\t\t\t\t\t\t\tC#: ");
//        sb.append(waitingCourseSummary[1]);
//        sb.append("\n\t\t\t\t\t\t\t\t\t\tData: ");
//        sb.append(waitingCourseSummary[2]);
//        sb.append("\n\t\t\t\t\t\t\t\t\t\tDevOps: ");
//        sb.append(waitingCourseSummary[3]);
//        sb.append("\n\t\t\t\t\t\t\t\t\t\tBusiness: ");
//        sb.append(waitingCourseSummary[4]);
//        if (waitingCourseSummary[5] != 0) {
//            sb.append("\n\t\t\t\t\t\t\t\t\t\tCould not retrieve course type: ");
//            sb.append(waitingCourseSummary[5]);
//        }
//
//        System.out.println(sb);



        /*
        Trying a new type, instead of this:

        NUMBER OF OPEN CENTRES:		Training Hub: 1
						        	Bootcamp: 0
						        	Tech Centre: 0

        NUMBER OF CLOSED CENTRES:	Training Hub: 0
						        	Bootcamp: 0
					        		Tech Centre: 0

        NUMBER OF FULL CENTRES:		Training Hub: 1
							        Bootcamp: 0
						        	Tech Centre: 0

		Do this:

						|   OPEN   |   FULL   |  CLOSED  |
		Training Hub    |          |          |          |
		Bootcamp        |          |          |          |
		Tech Centre     |          |          |          |
		                  <- 10 ->                          ~~~> space x2, digit x6, space x2
         */

        StringBuilder display = new StringBuilder();
        display.append("\t\t\t\t|   OPEN   |   FULL   |  CLOSED  |\n");
        display.append(String.format("Training Hub\t|  %6d  |  %6d  |  %6d  |\n", openHub, fullHub, closedHub ));
        display.append(String.format("Bootcamp\t\t|  %6d  |  %6d  |  %6d  |\n", openBootcamp, fullBootcamp, closedBootcamp ));
        display.append(String.format("Tech Centre\t\t|  %6d  |  %6d  |  %6d  |\n\n", openTech, fullTech, closedTech ));

        /*
        instead of this:
        NUMBER OF TRAINEES CURRENTLY TRAINING:	Java: 23
										C#: 13
										Data: 19
										DevOps: 41
										Business: 19

        NUMBER OF TRAINEES CURRENTLY WAITING:	Java: 17
										C#: 14
										Data: 16
										DevOps: 19
										Business: 13

		do this:

		            |  BENCH   | TRAINING |  WAITING |
		Java        |          |          |          |
		C#:         |          |          |          |
		Data:       |          |          |          |
		DevOps:     |          |          |          |
		Business:   |          |          |          |
		Unknown     |          |          |          |

         */
        display.append("\t\t\t\t|  BENCH   | TRAINING |  WAITING |\n");
        display.append(String.format("Java\t\t\t|  %6d  |  %6d  |  %6d  |\n", benchCourseSummary[0], trainingCourseSummary[0], waitingCourseSummary[0] ));
        display.append(String.format("C#\t\t\t\t|  %6d  |  %6d  |  %6d  |\n", benchCourseSummary[1], trainingCourseSummary[1], waitingCourseSummary[1] ));
        display.append(String.format("Data\t\t\t|  %6d  |  %6d  |  %6d  |\n", benchCourseSummary[2], trainingCourseSummary[2], waitingCourseSummary[2] ));
        display.append(String.format("DevOps\t\t\t|  %6d  |  %6d  |  %6d  |\n", benchCourseSummary[3], trainingCourseSummary[3], waitingCourseSummary[3] ));
        display.append(String.format("Business\t\t|  %6d  |  %6d  |  %6d  |\n", benchCourseSummary[4], trainingCourseSummary[4], waitingCourseSummary[4] ));

        if (benchCourseSummary[5] > 0 || trainingCourseSummary[5] > 0 || waitingCourseSummary[5] > 0) {
            display.append(String.format("Unknown\t\t\t|  %6d  |  %6d  |  %6d  |\n", benchCourseSummary[5], trainingCourseSummary[5], waitingCourseSummary[5] ));
        }

        System.out.println(display);
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