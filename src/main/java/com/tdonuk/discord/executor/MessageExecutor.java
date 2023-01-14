package com.tdonuk.discord.executor;

import net.dv8tion.jda.api.events.GenericEvent;

public interface MessageExecutor <T> {
    T execute(GenericEvent command) throws Exception;
}
