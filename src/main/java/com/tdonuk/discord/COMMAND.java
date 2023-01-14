package com.tdonuk.discord;

import com.tdonuk.discord.executor.MessageExecutor;
import com.tdonuk.discord.executor.NewsAPIExecutor;

public enum COMMAND {
    NEWS("!n", NewsAPIExecutor.instance());

    private final String name;
    private final MessageExecutor executor;
    COMMAND(String name, MessageExecutor executor) {
        this.name = name;
        this.executor = executor;
    }

    public String getName() {
        return name;
    }

    public MessageExecutor getExecutor() {
        return executor;
    }
}
