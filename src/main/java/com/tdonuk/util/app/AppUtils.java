package com.tdonuk.util.app;

import com.tdonuk.config.JDAConfig;
import com.tdonuk.config.NewsAPIConfig;
import com.tdonuk.constant.Globals;
import com.tdonuk.util.BaseUtils;

import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;


public final class AppUtils extends BaseUtils {
    public static void init() {
        Map<String, String> environment = System.getenv();

        JDAConfig.JDA_API_KEY = environment.get(Globals.JDA_API_KEY);
        NewsAPIConfig.NEWS_API_KEY = environment.get(Globals.NEWS_API_KEY);

        // configure logger
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINER);
        logger.setLevel(Level.FINER);
        logger.addHandler(consoleHandler);
    }
}
