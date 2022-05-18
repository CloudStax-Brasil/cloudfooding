package com.cloudstax.cloudfooding;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logs {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fileHandler;


        try {
            fileHandler = new FileHandler("src/main/resources/MyLogFile.txt");
            logger.addHandler(fileHandler);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
            logger.info("Log test");

        } catch (SecurityException e) {
            logger.info("Exception:" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.info("IO Exception:" + e.getMessage());
            e.printStackTrace();
        }
        logger.info("Na main class");
    }
}

