package org.example;

public class Water {
    private double salinity;

    public Water(double salinity) {
        setSalinity(salinity);
    }

    public double getSalinity() { return salinity; }

    public void setSalinity(double salinity) {
        if (salinity < 0) throw new IllegalArgumentException("Salinity cannot be negative");
        this.salinity = salinity;
    }
}
