package com.uam.decorator.fastfood;

public class ProductWithExtraMeat implements Product {

    private final double extraCost;
    private final Product baseProduct;

    public ProductWithExtraMeat(Product baseProduct) {
        this.baseProduct = baseProduct;
        this.extraCost = 2.5;
    }

    @Override
    public double getCost() {
        return baseProduct.getCost() + extraCost;
    }

    public void describe() {
        System.out.print(" [extra meat]");
    }
}