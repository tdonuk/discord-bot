package com.tdonuk.config;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Arrays;
import java.util.List;

public class JDAConfig extends App {
    public static String JDA_API_KEY = "PROTECTED";
    public static final List<GatewayIntent> INTENTS = Arrays.asList(GatewayIntent.MESSAGE_CONTENT);
    public static final Activity ACTIVITY = Activity.competing("Hello World");
    public static final OnlineStatus ONLINE_STATUS = OnlineStatus.IDLE;
}
