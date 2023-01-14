package com.tdonuk.discord.executor;

import net.dv8tion.jda.api.events.GenericEvent;

public interface MessageExecutor {
    void execute(GenericEvent event) throws Exception;
}
