package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Writes aquariums, fish and users to a txt file, one object per line
public class DataSaver {

    public static void saveToFile(String fileName, List<Aquarium> aquariums, List<User> users) {
        List<String> lines = new ArrayList<>();

        for (Aquarium a : aquariums) {
            lines.add("AQUARIUM," + a.getName() + "," + a.isSaltWater() + "," + a.getWater().getSalinity());
            for (Fish f : a.getFishList()) {
                lines.add("FISH," + f.getName() + "," + f.getSpecies() + "," + f.getColor() + ","
                        + f.getAge() + "," + f.getLength() + "," + f.getWeight() + ","
                        + f.isHungry() + "," + a.getName());
            }
        }
        for (User u : users) {
            String role = (u instanceof Owner) ? "OWNER" : "AQUARIST";
            lines.add(role + "," + u.getName());
        }

        try {
            Files.write(Paths.get(fileName), lines);   // creates the file or overwrites it
            System.out.println("Saved " + lines.size() + " lines to " + fileName);
        } catch (IOException e) {
            System.out.println("Could not write " + fileName + ": " + e.getMessage());
        }
    }

    // prints the file back to the console
    public static void printFile(String fileName) {
        try {
            for (String line : Files.readAllLines(Paths.get(fileName))) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Could not read " + fileName + ": " + e.getMessage());
        }
    }
}