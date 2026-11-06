package org.example;
import java.util.*;

public class AquariumService {
    private final List<Aquarium> aquariums = new ArrayList<>();
    private final Map<Integer, Fish> fishById = new HashMap<>();
    private final Map<Species, List<Fish>> fishBySpecies = new HashMap<>();
    private final Queue<Fish> feedingQueue = new LinkedList<>();
    private final Feeder feeder = new Feeder();        // - Block D

    public void addAquarium(Aquarium aquarium) {
        if(!aquariums.contains(aquarium))
            aquariums.add(aquarium);
    }

    // Create -*
    public boolean registerFishToAquarium(Aquarium aquarium, Fish fish) {
        if (fishById.containsKey(fish.getId())) {
            System.out.println(fish.getName() + " is already registered");
            return false;
        }
        if (!aquarium.addFish(fish)) return false;   //checking salinity
        if (!aquariums.contains(aquarium)) aquariums.add(aquarium);   //checking if the aquarium exists
        fishById.put(fish.getId(), fish);
        List<Fish> list = fishBySpecies.get(fish.getSpecies());   //the link
        if (list == null) {
            list = new ArrayList<>();
            fishBySpecies.put(fish.getSpecies(), list);
        }
        list.add(fish);
        return true;
    }

    public Fish findFishById(int id) {
        Fish fish = fishById.get(id);
        if (fish == null) throw new NoSuchElementException("No fish with id " + id);
        return fish;
    }

    public List<Fish> getFishBySpecies(Species species) {
        return fishBySpecies.getOrDefault(species, new ArrayList<>());
    }

    public Aquarium findAquariumById(int id) {
        for (Aquarium a : aquariums) {
            if (a.getId() == id) return a;
        }
        throw new NoSuchElementException("Aquarium with id " + id + " not found");
    }

    //  ---
    public void requestFeeding(int fishId) {
        feedingQueue.offer(findFishById(fishId));
    }

    public void processNextFeeding() {
        Fish fish = feedingQueue.poll();     // null if empty
        if (fish == null) {
            System.out.println("Queue is empty");
            return;
        }
        feeder.feed(fish);
    }

    public void celebrateBirthday(Fish fish) {
        if (fish.getAge() + 1 > Fish.MAX_AGE) {
            removeFish(fish.getId());
            System.out.println(fish.getName() + " lived a long life and was removed");
        } else {
            fish.celebrateBirthday();
        }
    }

    // * * * * CRUD
    // - Create - *
    public Aquarium createAndAddAquarium(String name, boolean saltWater, double salinity) {
        Aquarium newAquarium = new Aquarium(name, saltWater, salinity);
        aquariums.add(newAquarium);
        return newAquarium;
    }

    // - Delete
    public void removeFish(int id) {
        Fish fish = findFishById(id);
        fishById.remove(id);     //^
        fishBySpecies.get(fish.getSpecies()).remove(fish);    //^
        for (Aquarium a : aquariums) a.removeFish(fish);
        feedingQueue.removeIf(f -> f.equals(fish));
    }

    // - Update
    public void changeSpecies(int id, Species newSpecies) {
        if (newSpecies == null)
            throw new IllegalArgumentException("Setting a species is mandatory!");
        Fish fish = findFishById(id);
        fishBySpecies.get(fish.getSpecies()).remove(fish);
        fish.setSpecies(newSpecies);
        fishBySpecies.computeIfAbsent(newSpecies, k -> new ArrayList<>()).add(fish);
    }

    public void updateWeight(int id, double newWeight) {
        findFishById(id).setWeight(newWeight);
    }

    // - Read  (4. search & filter)
    public List<Fish> filterHungry() {
        List<Fish> result = new ArrayList<>();
        for (Fish f : fishById.values()) if (f.isHungry()) result.add(f);
        return result;
    }

    public List<Fish> getSorted(Comparator<Fish> comparator) {
        List<Fish> list = new ArrayList<>(fishById.values());
        list.sort(comparator);   // null = естественный порядок
        return list;
    }

    public void printReport() {
        if (fishById.isEmpty()) {
            System.out.println("No fish registered");
            return;
        }
        double totalWeight = 0;
        for (Fish f : fishById.values())
            totalWeight += f.getWeight();
        System.out.println("Total fish: " + fishById.size());
        System.out.println("Average weight: " + totalWeight / fishById.size());
        for (Map.Entry<Species, List<Fish>> e : fishBySpecies.entrySet())
            System.out.println(e.getKey() + ": " + e.getValue().size());
    }
}
