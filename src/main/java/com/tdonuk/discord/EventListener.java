package com.tdonuk.discord;

import com.tdonuk.constant.Globals;
import com.tdonuk.util.discord.MessageUtils;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveAllEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.user.UserTypingEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

public class EventListener extends ListenerAdapter {

    private final Logger logger = Logger.getGlobal();

    @Override
    public void onReady(ReadyEvent event) {
        logger.info("droid-x is ready to go!");
    }

    @Override
    public void onUserTyping(UserTypingEvent event) {
        logger.info(event.getUser().getName() + " is started typing..");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        logger.entering(this.getClass().getName(), "onMessageReceived", event);

        if(event.getAuthor().isBot()) {
            return;
        }

        if(event.getMessage().getContentDisplay().equalsIgnoreCase("!are you alive?")) {
            event.getMessage().reply("Bip-boop").queue(); // heartbeat check (can be used like -ping)
            return;
        }

        if(event.getChannelType().equals(ChannelType.PRIVATE) && !event.getAuthor().getName().contains("Taha Dönük")) {
            logger.info(event.getAuthor().getName()+": " + event.getMessage().getContentDisplay());
            event.getMessage().reply("Sorry, i'm not created to chat with a human. I have neither intelligence nor consciousness.").queue();
            return;
        }

        logger.info(event.getAuthor().getName()+": " + event.getMessage().getContentDisplay());

        String message = event.getMessage().getContentDisplay();

        if(Arrays.asList("nabarsın","nabarsınız", "nabonuz", "!nabarsın", "!nabarsınız","!nabonuz").contains(message)) {
            event.getMessage().reply("Seni").queue();
            return;
        }

        if(!message.startsWith("!")) return;

        if(message.equals("!help") || message.equals("!example")) {
            event.getMessage().reply(MessageUtils.list("Here is an example of usage", Globals.mobalyticsTutorials));
            return;
        }

        COMMAND command = COMMAND.byName(message.substring(0,message.indexOf(" "))); // (!n command) -> 0: !, 1: n

        try {
            command.getExecutor().execute(event);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            event.getMessage().reply(MessageUtils.italic("Sorry, a problem has occurred while searching for the web. Biip-bop.")).queue();
        }

        logger.exiting(this.getClass().getName(), "onMessageReceived");
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
        System.out.println("[MESSAGE EDIT]\t"+event.getAuthor().getName()+": " + event.getMessageId());
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        System.out.println("[MESSAGE DELETE]\t"+event.getMessageId());
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        System.out.println("[MESSAGE REACTION]\t"+event.getEmoji());
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        System.out.println("[MESSAGE REACTION DELETE]\t"+event.getEmoji());
    }

    @Override
    public void onMessageReactionRemoveAll(MessageReactionRemoveAllEvent event) {
        System.out.println("[MESSAGE REACTION ALL DELETE]\t"+event.getMessageId()+ " all reactions removed");
    }
}
