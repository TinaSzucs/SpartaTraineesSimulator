package com.sparta.simulator.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogDriver {
    public static Logger logger = LogManager.getLogger("Logger");

    public static void traceLog(String message) {
        logger.trace(message);
    }

    public static void debugLog(String message) {
        logger.debug(message);
    }

    public static void errorLog(String message) {
        logger.error(message);
    }

    public static void main(String[] args) {
        logger.info("Program started");
    }
}
