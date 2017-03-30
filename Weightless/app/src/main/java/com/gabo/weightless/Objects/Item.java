package com.gabo.weightless.Objects;

/**
 * Created by MiguelAngel on 29/03/2017.
 */

public class Item {

    private String name;
    private int qty, id, categoryID;
    private double weight;

    public Item(String n, double w, int q, int id, int cID) {
        this.weight = w;
        this.qty = q;
        this.name = n;
        this.id = id;
        this.categoryID = cID;
    }

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        return this.weight;
    }

    public int getQty() {
        return this.qty;
    }

    public int getID() {
        return this.id;
    }

    public int getCategoryID() {
        return this.categoryID;
    }
}
