package com.tdonuk;

import com.tdonuk.config.JDAConfig;
import com.tdonuk.discord.EventListener;
import com.tdonuk.util.app.AppUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.util.Scanner;

public class Main {

    private final String NEWS_API_KEY = "[PROTECTED]";
    public static void main(String[] args) throws InterruptedException {
        AppUtils.init();

        JDA jda = JDABuilder.createDefault(JDAConfig.JDA_API_KEY).setActivity(JDAConfig.ACTIVITY).enableIntents(JDAConfig.INTENTS).setStatus(JDAConfig.ONLINE_STATUS).build();
        jda.addEventListener(new EventListener());
        jda.awaitReady();

        // send messages from bot using console input
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while(1 == 1) {
                try {
                    String[] adminMessage = scanner.nextLine().split("_");
                    jda.getGuildsByName(adminMessage[0], true).get(0).getTextChannelsByName(adminMessage[1], true).get(0).sendMessage(adminMessage[2]).queue();
                } catch (Exception e) {
                    continue;
                }
            }
        }).start();
    }
}
