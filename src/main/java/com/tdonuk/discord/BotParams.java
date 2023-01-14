package com.tdonuk.discord;

import java.util.Map;

/**
 * Command params
 */
public class BotParams {
    public static final String Q = "-q";
    public static final String C = "-c";
    public static final String NEWS = "-t";

    public static final Map<String, String> newsApiTutorials = Map.of(
            "!n -t", "Fetches the top 10 breaking news",
            "!n -c sport,politics", "Fetches the top 10 breaking news with given categories",
            "!n -q president kidnapped", "Searches for the given keywork among all news headers"
            );

}
