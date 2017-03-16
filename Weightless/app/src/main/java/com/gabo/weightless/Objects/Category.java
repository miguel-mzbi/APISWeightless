package com.gabo.weightless.Objects;

/**
 * Created by miguel on 15/03/17.
 */

public class Category {
    private String name;
    private int id, equipmentID;

    public Category(int id, String name, int eID) {
        this.id = id;
        this.name = name;
        this.equipmentID = eID;
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

}

