package com.uam.decorator.fastfood;

import java.util.UUID;

public class Cheeseburger implements Product {

    private final UUID orderId;

    private final double cost;

    public Cheeseburger() {
        this.orderId = UUID.randomUUID();
        this.cost = 3.5;
    }

    @Override
    public void describe() {
        System.out.print("[" + orderId + "] Cheeseburger");
    }

    @Override
    public double getCost() {
        return cost;
    }
}