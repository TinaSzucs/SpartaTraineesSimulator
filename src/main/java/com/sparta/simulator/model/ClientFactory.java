package com.sparta.simulator.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ClientFactory {

    public static ArrayList<Client> generateClient(int min, int max) {

        Random rand = new Random();
        int clientAmount = rand.nextInt(min, max+1);

        ArrayList<Client> newClient = new ArrayList<>();

        for(int i = 0; i < clientAmount; i++) {
            Course courseType = Course.values()[rand.nextInt(0, Course.values().length)];
            int requirementNumber = rand.nextInt(15,50);
            Client clientToAdd = new Client(courseType, requirementNumber);
            newClient.add(clientToAdd);
        }
        return newClient;
    }
}
