package com.uam.templatemethod.logger;


import java.time.Instant;

public abstract class AbstractLogger implements Logger {

    protected final String className;

    public AbstractLogger(String className) {
        this.className = className;
    }

    @Override
    public void info(String text) {
        log(format(text), Level.INFO);
    }

    @Override
    public void error(String text) {
        log(format(text), Level.ERROR);
    }

    private String format(String text) {
        return Instant.now() + " [" + className + "]: " + text;
    }

    public abstract void log(String text, Level level);
}
