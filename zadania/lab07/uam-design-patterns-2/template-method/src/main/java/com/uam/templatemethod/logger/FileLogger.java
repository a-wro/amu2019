package com.uam.templatemethod.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

public class FileLogger extends AbstractLogger {

    public FileLogger(String className) {
        super(className);
    }

    public void log(String text, Level level) {
        String currentFileName = calculateCurrentFileName(level);
        Path pathToLogFile = Paths.get(currentFileName);

        if (Files.exists(pathToLogFile)) {
            writeToFile(pathToLogFile, text, StandardOpenOption.APPEND);
        } else {
            writeToFile(pathToLogFile, text, StandardOpenOption.CREATE);
        }
    }

    private String calculateCurrentFileName(Level level) {
        return "log-" + LocalDate.now() + (level.equals(Level.INFO) ? "-infos" : "-errors");
    }

    private void writeToFile(Path path, String textToPrint, StandardOpenOption standardOpenOption) {
        try {
            Files.write(path, (textToPrint + "\n").getBytes(), standardOpenOption);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
