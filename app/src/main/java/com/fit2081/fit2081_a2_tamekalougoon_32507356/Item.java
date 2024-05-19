package com.fit2081.fit2081_a2_tamekalougoon_32507356;

public class Item {
    String name;
    int quantity;
    int cost;


    // constructor
    public Item(String name, int quantity, int cost) {
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
    }

    // getters

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCost() {
        return cost;
    }

}