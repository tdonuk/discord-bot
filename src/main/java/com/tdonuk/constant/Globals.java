package com.tdonuk.constant;

import com.tdonuk.constant.lol.ROLE;
import com.tdonuk.discord.COMMAND;
import com.tdonuk.util.discord.MessageUtils;

import java.util.Map;

/**
 * Variable names etc..
 */
public class Globals {
    public static final String JDA_API_KEY = "JDA_API_KEY";
    public static final String NEWS_API_KEY = "NEWS_API_KEY";

    public static final String HELP = "help";
    public static final String EXAMPLE = "example";

    public static final Map<String, String> mobalyticsTutorial = Map.of(
            String.format("**%s %s pantheon**\t*%s*", COMMAND.CT.getName(), ROLE.SUPP.getName(), "Fetches the counters of the given champion in a given role"), ""
    );

    public static final Map<String, String> newsTutorial = Map.of(
            String.format("**!news**\t*%s*", "Fetches top 4-10 (depending on size) of news"), ""
    );
}
