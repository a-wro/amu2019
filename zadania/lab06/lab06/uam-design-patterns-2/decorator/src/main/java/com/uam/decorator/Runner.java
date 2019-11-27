package com.uam.decorator;

import com.uam.decorator.fastfood.Cheeseburger;
import com.uam.decorator.fastfood.Product;
import com.uam.decorator.fastfood.ProductWithExtraCheese;
import com.uam.decorator.fastfood.ProductWithExtraMeat;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Runner {

    private static Logger logger = Logger.getLogger(Runner.class.getName());

    public static void main(String[] args) {

        Cheeseburger cheeseburger = new Cheeseburger();

        Product cheeseburgerWithExtraCheese = new ProductWithExtraCheese(cheeseburger);
        Product cheeseburgerWithExtraMeat = new ProductWithExtraMeat(cheeseburger);

        logger.log(Level.INFO, "Price of a basic cheeseburger: "
                + cheeseburger.getCost());

        logger.log(Level.INFO, "Price of a cheeseburger with extra cheese: "
                + cheeseburgerWithExtraCheese.getCost());

        logger.log(Level.INFO, "Price of a cheeseburger with extra meat: "
                + cheeseburgerWithExtraMeat.getCost());

    }
}
