package com.sparta.simulator.model;

import com.sparta.simulator.logging.LogDriver;

import java.util.ArrayList;
import java.util.Random;

public class ClientFactory {

    public static ArrayList<Client> generateClient(int min, int max) {
        Random rand = new Random();
        int clientAmount = rand.nextInt(min, max+1);
        StringBuilder sb = new StringBuilder(String.format("Generating %2d amount of client(s), which is in between the given %2d and %2d limit.\n", clientAmount, min, max));

        ArrayList<Client> newClient = new ArrayList<>(clientAmount);

        for(int i = 0; i < clientAmount; i++) {
            Course courseType = Course.values()[rand.nextInt(0, Course.values().length)];

            int requirementNumber = rand.nextInt(15,50);

            Client clientToAdd = new Client(courseType, requirementNumber);
            newClient.add(clientToAdd);

            sb.append(String.format("New client have been generated with %3d %8S requirements.\n", requirementNumber, courseType));
        }

        LogDriver.debug(sb.toString());
        return newClient;
    }
}
