package org.example;
import org.example.Fish.Swimmable;
import org.example.Fish.SalinitySensitive;

public class SaltwaterFish extends Fish implements Swimmable, SalinitySensitive {
    private double minSaltyLevel;
    private double maxSaltyLevel;

    public SaltwaterFish(String name, Species species, String color, double age, double length,
                          double weight, boolean isHungry, double minSaltyLevel, double maxSaltyLevel) {
        super(name, species, color, age, length, weight, isHungry);
        if (minSaltyLevel < 0 || maxSaltyLevel > 100 || minSaltyLevel > maxSaltyLevel)
            throw new IllegalArgumentException("Invalid salinity range");
        this.minSaltyLevel = minSaltyLevel;
        this.maxSaltyLevel = maxSaltyLevel;
    }

    public double getMinSaltyLevel() { return minSaltyLevel; }
    public double getMaxSaltyLevel() { return maxSaltyLevel; }

    public void setMinSaltyLevel(double minSaltyLevel) {
        if(minSaltyLevel < 0 || minSaltyLevel > maxSaltyLevel)
            throw new IllegalArgumentException("Min salt level for fish cannot be negative");
        this.minSaltyLevel = minSaltyLevel;
    }
    public void setMaxSaltyLevel(double maxSaltyLevel) {
        if(maxSaltyLevel > 100 || maxSaltyLevel < minSaltyLevel)
            throw new IllegalArgumentException("Max salt level for fish cannot be more than 100");
        this.maxSaltyLevel = maxSaltyLevel;
    }

    @Override
    public double calculateDailyFood() { return getWeight() * 0.05; }
    @Override
    public int getFeedingsPerDay() { return 3; }

    //unique method  |
    //              \|/
    //               `

    //interfaces implementation
    @Override
    public void swim() { System.out.println(getName() + " is swimming evenly, writhing and waving its tail o 0 o");}
    @Override
    public boolean checkSalinity(double currentSalinity) {
        if (currentSalinity >= minSaltyLevel && currentSalinity <= maxSaltyLevel) {
            System.out.println(getName() + " is thriving at a salinity level of " + currentSalinity + " ppt.");
            return true;
        } else {
            System.out.println("WARNING! Salinity level of " + currentSalinity + " ppt is dangerous for " + getName() + "!");
            return false;
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("- * Min salt level: " + minSaltyLevel);
        System.out.println("- * Max salt level: " + maxSaltyLevel);
    }
}
