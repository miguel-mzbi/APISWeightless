package com.gabo.weightless.Objects;

/**
 * Created by miguel on 15/03/17.
 */

public class Category {
    private String name;
    private int id, equipmentID;
    private double totalWeight;

    public Category(int id, String name, int eID, double tw) {
        this.id = id;
        this.name = name;
        this.equipmentID = eID;
        this.totalWeight = tw;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.id;
    }

    public int getEquipmentID() {
        return this.equipmentID;
    }

    public double getCategoryWeight() {
        return this.totalWeight;
    }

}

