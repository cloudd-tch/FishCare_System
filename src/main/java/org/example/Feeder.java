package org.example;

//for dependency demonstration
public class Feeder {
    public void feed(Fish fish) {
        if(!fish.isHungry()) {
            System.out.println(fish.getName() + " is not hungry, skipping");
            return;
        }
        fish.feed();
        System.out.println(fish.getName() + " was fed: " + fish.calculateDailyFood() / fish.getFeedingsPerDay() + " g.");
    }

    public void feedAll(Aquarium aquarium) {
        for(Fish fish : aquarium.getFishList()) {
            feed(fish);
        }
    }
}
