package org.example;
import java.util.*;

import org.example.Fish.SalinitySensitive;

public class Aquarium {
    private static int nextId = 1;
    private final int id = nextId++;
    private String name;
    private final boolean saltWater;
    private final Water water;                            //composition
    private final List<Fish> fishList = new ArrayList<>();      //aggregation, 1:N
    private final Set<Aquarist> aquarists = new HashSet<>();   //*~

    private static int defaultCounter = 1;

    public Aquarium(String name, boolean saltWater, double salinity) {
        setName(name);
        this.saltWater = saltWater;
        this.water = new Water(saltWater ? salinity: 0);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public List<Fish> getFishList() { return fishList; }
    public Water getWater() { return water; }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) this.name = name;
        else this.name = "Aquarium #" + defaultCounter++;
    }

    // *~ for N:M
    public void addAquarist(Aquarist a) { aquarists.add(a); }
    public Set<Aquarist> getAquarists() { return aquarists; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Aquarium aquarium = (Aquarium) o;
        return id == aquarium.id;
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public boolean addFish(Fish fish) {
        boolean seaFish = fish instanceof SaltwaterFish;
        if(seaFish != saltWater) {
            System.out.println("Cannot add " + fish.getName() + " - wrong water type!");
            return false;
        } if (fish instanceof SalinitySensitive && !((SalinitySensitive) fish).checkSalinity(water.getSalinity())) {
            return false;
        }
            fishList.add(fish);
            return true;
    }

    public void removeFish(Fish fish) { fishList.remove(fish); }

    public boolean isSaltWater() { return saltWater; }
}
