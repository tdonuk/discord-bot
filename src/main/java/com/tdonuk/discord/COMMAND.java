package com.tdonuk.discord;

import com.tdonuk.discord.executor.HelpExecutor;
import com.tdonuk.discord.executor.MessageExecutor;
import com.tdonuk.discord.executor.MobalyticsExecutor;
import com.tdonuk.discord.executor.NewsAPIExecutor;

public enum COMMAND {
    NEWS("!news", NewsAPIExecutor.instance()), CT("!ct", MobalyticsExecutor.instance()), HELP("!help", HelpExecutor.instance());

    private final String name;
    private final MessageExecutor executor;
    COMMAND(String name, MessageExecutor executor) {
        this.name = name;
        this.executor = executor;
    }

    public static COMMAND byName(String name) {
        return COMMAND.valueOf(name.substring(1).toUpperCase());
    }

    public String getName() {
        return name;
    }

    public MessageExecutor getExecutor() {
        return executor;
    }
}
