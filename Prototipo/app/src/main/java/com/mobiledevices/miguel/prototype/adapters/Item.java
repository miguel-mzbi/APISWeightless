package com.mobiledevices.miguel.prototype.adapters;

public class Item {

    private String name;
    private String desc;
    private int qty;
    private int weight;
    private String measure;

    public Item(String name, String desc, int qty, int weight, String measure) {

        this.name = name;
        this.desc = desc;
        this.qty = qty;
        this.weight = weight;
        this.measure = measure;
    }

    public String getName() {

        return this.name;
    }

    public String getDesc() {

        return this.desc;
    }

    public String getQty() {

        return this.qty+"";
    }

    public String getWeight() {

        return this.weight+"";
    }

    public String getMeasure() {

        return this.measure;
    }
}
