package com.gabo.weightless.Objects;

/**
 * Created by LIC on 08/03/2017.
 */

public class Equipment {

    private String name, owner;
    private int id;
    private double totalWeight;

    public Equipment(int id, String name, String owner, double totalWeight) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.totalWeight = totalWeight;
    }

    public String getName(){
        return this.name;
    }
    public String getOwner(){
        return this.owner;
    }
    public int getId(){
        return this.id;
    }

    public double getTotalWeight() {
        return totalWeight;
    }
}
