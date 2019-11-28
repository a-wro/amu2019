package com.uam.decorator.fastfood;

public class ProductWithExtraCheese implements Product {
    
    private final double extraCost;
    private final Product baseProduct;

    public ProductWithExtraCheese(Product baseProduct) {
        this.baseProduct = baseProduct;
        this.extraCost = 0.5;
    }

    @Override
    public double getCost() {
        return baseProduct.getCost() + extraCost;
    }

    public void describe() {
        System.out.print(" [extra cheese]");
    }
}