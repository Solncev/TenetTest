package com.solncev.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Марат on 02.07.2017.
 */
public class TweetLogger {
    public static void log(String message) {
        try {
            boolean append = true;
            FileHandler handler = null;
            handler = new FileHandler("\\TwitterSearch\\src\\main\\resources\\logs\\default.log", append);
            Logger logger = Logger.getLogger("com.solncev.util");
            logger.addHandler(handler);
            logger.info("downloaded " + message + " tweets");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
