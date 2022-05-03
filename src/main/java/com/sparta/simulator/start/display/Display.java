package com.sparta.simulator.start.display;

import java.util.Scanner;

public class Display {

    public static void mainDisplay() {
        Scanner input = new Scanner(System.in);
        int noFullCentres, noOpenCentres, noInTraining, noInWaitingList;
        noFullCentres = noOpenCentres = noInTraining = noInWaitingList = 0;

        System.out.println("Enter the number of months to run the simulation for: ");

        int length = input.nextInt();

        // run code

        System.out.println("Number of full training centres: " + noFullCentres);
        System.out.println("Number of open training centres: " + noOpenCentres);
        System.out.println("Number of trainees in training: " + noInTraining);
        System.out.println("Number of trainees in waiting list: " + noInWaitingList);
    }
}
