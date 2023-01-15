package com.tdonuk.discord.executor;

import com.tdonuk.discord.COMMAND;
import com.tdonuk.util.discord.MessageUtils;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.naming.OperationNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HelpExecutor extends AbstractMessageExecutor{
    private static HelpExecutor executor;

    public static MessageExecutor instance() {
        if(Objects.isNull(executor)) executor = new HelpExecutor();

        return executor;
    }

    @Override
    public void execute(GenericEvent event) throws Exception {
        logger.entering(this.getClass().getName(), "execute", event);

        MessageReceivedEvent messageEvent = (event instanceof MessageReceivedEvent) ? (MessageReceivedEvent) event : null; // currently not supporting event other than message

        if(Objects.isNull(messageEvent)) throw new OperationNotSupportedException("This operation is not supported currently");

        StringBuilder tutorial = new StringBuilder("Here are the list of commands you can use\n\n");

        Map<String, String> listMap = new HashMap<>();

        for(COMMAND cmd : COMMAND.values()) {
            if(!COMMAND.HELP.equals(cmd)) listMap.put(MessageUtils.bold(cmd.getName()),"");
        }

        tutorial.append(MessageUtils.list(listMap));
        tutorial.append("\nyou can use "+ MessageUtils.italic_bold("!<command> help") + " to get an example of the given command");

        messageEvent.getMessage().reply(tutorial.toString()).queue();

        logger.exiting(this.getClass().getName(), "execute");
    }
}
