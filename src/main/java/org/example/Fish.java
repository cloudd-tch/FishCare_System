package org.example;

import java.util.Objects;

public abstract class Fish implements Comparable<Fish> {
    private static int nextId = 1;
    private final int id = nextId++;
    private String name;
    private Species species;
    private String color;
    private double age;
    private double length;           //in cm
    private double weight;           //in gr
    private boolean isHungry;        //-

    //default
    public Fish() {
        this.name = "No name";
        this.species = Species.GOLDFISH;
        this.color = "unknown";
        this.age = 0;
        this.length = 1;
        this.weight = 1;
        this.isHungry = true;
    }

    public Fish(String name, Species species, String color, double age, double length, double weight, boolean isHungry) {
        setName(name);
        setSpecies(species);
        setColor(color);
        setAge(age);
        setLength(length);
        setWeight(weight);
        this.isHungry = isHungry;
    }

    public static final int MAX_AGE = 20;

    //setters
    public void setName(String name) {
        if(name == null || name.isBlank())
          throw new IllegalArgumentException("Name cannot be null");
        this.name = name;
    }
    public void setSpecies(Species species) {
        if (species == null)
            throw new IllegalArgumentException("Setting a species is mandatory!");
        this.species = species;
    }
    public void setColor(String color) {
        if (color == null || color.isBlank()) this.color = "grey";
        else this.color = color;
    }
    public void setAge(double age) {
        if (age < 0 || age > MAX_AGE)
            throw new IllegalArgumentException("Age cannot be negative or more than " + MAX_AGE + " years");
        this.age = age;
    }
    public void setLength(double length) {
        if(length <= 0 || length > 200)
            throw new IllegalArgumentException("Length cannot be negative or more than 200 cm");
        this.length = length;
    }
    public void setWeight(double weight) {
        if (weight <= 0 || weight > 1000)
            throw new IllegalArgumentException("Weight cannot be negative or more than 1000 gr");
        this.weight = weight;
    }
    // hungry state
    public void feed() { this.isHungry = false; }
    public void markHungry() { this.isHungry = true; }

    // getters
    public int getId() { return id; }
    public String getName() { return name; }
    public Species getSpecies() { return species; }
    public String getColor() { return color; }
    public double getAge() { return age; }
    public double getLength() { return length; }
    public double getWeight() { return weight; }
    public boolean isHungry() { return isHungry; }

    // methods
    public void displayInfo() {
        System.out.println("- - * o 0 o * - -");
        System.out.println("- * Name: " + name);
        System.out.println("- * Species: " + species);
        System.out.println("- * Color: " + color);
        System.out.println("- * Length: " + length + " centimeters");
        System.out.println("- * Age: " + age + " years old");
        System.out.println("- * Hungry level: " + (isHungry ? "hungry" : "normal"));
        System.out.println("- - * - - - * - -");
    }

    //*-*
    public void celebrateBirthday() {
        setAge(getAge() + 1);
        markHungry();
        System.out.println(getName() + " celebrated a birthday! o 0 * 0 ");
        System.out.println("o 0 * 0 new age: " + getAge());
    }

    // abstract methods
    public abstract double calculateDailyFood();
    public abstract int getFeedingsPerDay();

    // interfaces
    public interface Swimmable { void swim(); }   //just to add
    public interface SalinitySensitive {
        boolean checkSalinity(double currentSalinity);   //in saltwaterFish
    }

    //*-* SW1
    // - Block A
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fish fish = (Fish) o;
        return id == fish.id;
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    // - Block C
    @Override
    public int compareTo(Fish otherFish) {
        return Integer.compare(this.id, otherFish.id);
    }
}
