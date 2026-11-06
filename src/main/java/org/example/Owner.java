package org.example;

import java.util.ArrayList;
import java.util.List;

//making requests
public class Owner extends User {
    private final List<Aquarium> aquariums = new ArrayList<>();  // 1:N

    public Owner(String name) {
        super(name);
    }

    public void requestFeeding(AquariumService s, int fishId) {
        s.requestFeeding(fishId);
    }
    public void addAquarium(Aquarium a) { aquariums.add(a); }
}