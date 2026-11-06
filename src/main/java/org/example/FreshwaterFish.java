package org.example;
import org.example.Fish.Swimmable;

public class FreshwaterFish extends Fish implements Swimmable {
    private int minGroupSize;
    private boolean needsLivePlants;

    public FreshwaterFish(String name, Species species, String color, double age, double length,
                          double weight, boolean isHungry, int minGroupSize, boolean needsLivePlants) {
        super(name, species, color, age, length, weight, isHungry);
        setMinGroupSize(minGroupSize);
        this.needsLivePlants = needsLivePlants;
    }

    public int getMinGroupSize() { return minGroupSize; }
    public boolean isNeedsLivePlants() { return needsLivePlants; }

    public void setMinGroupSize(int minGroupSize) {
        if(minGroupSize < 1)
            throw new IllegalArgumentException("Min group size cannot be negative");
        this.minGroupSize = minGroupSize;
    }
    public void setNeedsLivePlants(boolean needsLivePlants) { this.needsLivePlants = needsLivePlants; }

    //---
    @Override
    public double calculateDailyFood() { return getWeight() * 0.03; }
    @Override
    public int getFeedingsPerDay() { return 2; }

    // unique method
    public boolean isGroupSizeSafe(int count) { return count >= minGroupSize; }

    // interface implementation ***
    public void swim() { System.out.println(getName() + " is swimming fast, writhing and waving its tail ~ o 0");}

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("- * Min group: " + minGroupSize);
        System.out.println("- * Live plants: " + needsLivePlants);
    }
}
