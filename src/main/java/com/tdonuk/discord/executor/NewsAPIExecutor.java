package com.tdonuk.discord.executor;

import com.tdonuk.http.dto.NewsResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.naming.OperationNotSupportedException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Fetch news from NewsAPI and return in a format of list to the channel that bot is being called
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NewsAPIExecutor extends AbstractMessageExecutor {
    private static NewsAPIExecutor executor;

    public static NewsAPIExecutor instance() {
        if(Objects.isNull(executor)) executor = new NewsAPIExecutor();

        return executor;
    }

    @Override
    public void execute(GenericEvent event) throws Exception {
        logger.entering(this.getClass().getName(), "execute", event);

        MessageReceivedEvent messageEvent = (event instanceof MessageReceivedEvent) ? (MessageReceivedEvent) event : null; // currently not supporting event other than message

        if(Objects.isNull(messageEvent)) throw new OperationNotSupportedException("This operation is not supported currently");

        String message = ((MessageReceivedEvent) event).getMessage().getContentDisplay();

        // TODO: do some business with received message..

        logger.exiting("NewsAPIExecutor", "execute");
    }
}
