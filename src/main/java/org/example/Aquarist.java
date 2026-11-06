package org.example;

import java.util.HashSet;
import java.util.Set;

//realising requests
public class Aquarist extends User {
    private final Set<Aquarium> assigned = new HashSet<>();    //N:M

    public Aquarist(String name) {
        super(name);
    }

    public void processNext(AquariumService s) { s.processNextFeeding(); }

    //*~
    public void assign(Aquarium a) {
        assigned.add(a);
        a.addAquarist(this);
    }
    public Set<Aquarium> getAssigned() { return assigned; }
}
