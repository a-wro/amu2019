package com.uam.templatemethod;


import com.uam.templatemethod.logger.ConsoleLogger;
import com.uam.templatemethod.logger.FileLogger;
import com.uam.templatemethod.logger.Logger;

public class Runner {


    private static void logStuff(Logger logger) {
        logger.info("First text to log as info");
        logger.info("Second text to log as info");
        logger.error("First text to log as error");
    }

    public static void main(String[] args) {
        String loggerName = Runner.class.getName();

        Logger logger;

        logger = new ConsoleLogger(loggerName);
        logStuff(logger);

        logger = new FileLogger(loggerName);
        logStuff(logger);

    }
}
