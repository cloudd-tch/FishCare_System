package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

// - Block C

class FishNameComparator implements Comparator<Fish> {  //1
    @Override
    public int compare(Fish o1, Fish o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}

class FishWeightComparator implements Comparator<Fish> {  //2
    @Override
    public int compare(Fish o1, Fish o2) {
        return Double.compare(o1.getWeight(), o2.getWeight());
    }
}

class FishAgeComparator implements Comparator<Fish> {  //3
    @Override
    public int compare(Fish f1, Fish f2) {
        return Double.compare(f1.getAge(), f2.getAge());
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("* AQUARIUM SYSTEM INITIALIZATION *");
        AquariumService service = new AquariumService();

        Owner owner = new Owner("Teddy (o)");
        Aquarist aquarist = new Aquarist("Emma (a)");

        // 2. Block A, composition with water
        Aquarium freshwaterTank = service.createAndAddAquarium("Community Planted Tank", false, 0.0);
        Aquarium marineTank = service.createAndAddAquarium("Coral Reef Sanctuary", true, 35.0);

        // N:M
        aquarist.assign(freshwaterTank);
        aquarist.assign(marineTank);
        owner.addAquarium(freshwaterTank);

        // 3. freshwater, saltwater with polymorphism
        FreshwaterFish fish1 = new FreshwaterFish("Goldy", Species.GOLDFISH, "golden", 1.5, 5.0, 15.0, true, 3, true);
        FreshwaterFish fish2 = new FreshwaterFish("Speedy", Species.GUPPY, "rainbow", 0.5, 3.0, 5.0, true, 5, false);
        SaltwaterFish fish3 = new SaltwaterFish("Fifi", Species.CLOWNFISH, "orange-white", 1.0, 4.0, 20.0, true, 30.0, 36.0);

        // Block A, Block B: indexing map
        service.registerFishToAquarium(freshwaterTank, fish1);
        service.registerFishToAquarium(freshwaterTank, fish2);
        service.registerFishToAquarium(marineTank, fish3);

        System.out.println("\n* SEARCH AND CRUD DEMONSTRATION (Block B) *");
        // O(1) lookup by id
        Fish foundFish = service.findFishById(fish1.getId());
        System.out.println("Found fish by ID " + fish1.getId() + ": " + foundFish.getName() + " (" + foundFish.getSpecies() + ")");

        // filtering
        List<Fish> hungryFish = service.filterHungry();
        System.out.println("Number of hungry fish: " + hungryFish.size());

        System.out.println("\n* REQUEST QUEUE AND USER ROLES DEMONSTRATION (Block D) *");
        // Owner requests feeding fish by id (adds to queue)
        owner.requestFeeding(service, fish1.getId());
        owner.requestFeeding(service, fish3.getId());

        // aquarist processes request queue (Queue.poll())
        System.out.println("Aquarist starts processing feeding queue:");
        aquarist.processNext(service); // feeds fish1
        aquarist.processNext(service); // feeds fish3
        aquarist.processNext(service); // attempt to feed from empty queue

        System.out.println("\n* SORTING DEMONSTRATION (Block C) *");
        // sorting comparator
        List<Fish> sortedByWeight = service.getSorted(new FishWeightComparator());
        System.out.println("Fish sorted by weight:");
        for (Fish f : sortedByWeight) {
            System.out.println(" - " + f.getName() + " (" + f.getWeight() + " g)");
        }

        System.out.println("\n* UPDATE *");
        service.updateWeight(fish1.getId(), 16.0);
        System.out.println(fish1.getName() + " currently weighs " + fish1.getWeight() + " g");

        System.out.println("\n* OTHER SORTS *");
        for (Fish f : service.getSorted(new FishNameComparator()))
            System.out.println(" - " + f.getName());
        for (Fish f : service.getSorted(new FishAgeComparator()))
            System.out.println(" - " + f.getName() + " " + f.getAge());
        for (Fish f : service.getSorted(null))
            System.out.println(" - #" + f.getId() + " " + f.getName());

        System.out.println("\n* EDGE CASES *");
        try {
            service.findFishById(9999);
        } catch (NoSuchElementException e) {
            System.out.println("Missing key: " + e.getMessage());
        }

        System.out.println("\n* ANALYTICS AND REPORTS *");
        service.printReport();

        System.out.println("\n* SYNCHRONOUS REMOVAL DEMONSTRATION (CRUD) *");
        System.out.println("Removing fish with ID: " + fish2.getId() + " (" + fish2.getName() + ")");
        service.removeFish(fish2.getId());
        service.printReport();

        System.out.println("\n* ADDING DATA IN FILES *");
        // aquariums
        Aquarium riverTank = service.createAndAddAquarium("River tank", false, 0);
        Aquarium coralTank = service.createAndAddAquarium("Coral tank", true, 35);

        // fish
        service.registerFishToAquarium(riverTank,
                new FreshwaterFish("Goldy", Species.GOLDFISH, "golden", 1.5, 5, 15, true, 3, true));
        service.registerFishToAquarium(riverTank,
                new FreshwaterFish("Speedy", Species.GUPPY, "rainbow", 0.5, 3, 5, false, 5, false));
        service.registerFishToAquarium(coralTank,
                new SaltwaterFish("Fifi", Species.CLOWNFISH, "orange-white", 1, 4, 20, true, 30, 36));

        // users
        Owner demoOwner = new Owner("Alex");
        demoOwner.addAquarium(riverTank);
        demoOwner.addAquarium(coralTank);
        Aquarist demoAquarist = new Aquarist("John");
        demoAquarist.assign(riverTank);
        demoAquarist.assign(coralTank);

        // collect and save
        List<Aquarium> aquariumsToSave = new ArrayList<>();
        aquariumsToSave.add(riverTank);
        aquariumsToSave.add(coralTank);

        List<User> usersToSave = new ArrayList<>();
        usersToSave.add(demoOwner);
        usersToSave.add(demoAquarist);

        DataSaver.saveToFile("saved_data.txt", aquariumsToSave, usersToSave);
        DataSaver.printFile("saved_data.txt");
    }
}