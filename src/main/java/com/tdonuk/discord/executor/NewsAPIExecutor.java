package com.tdonuk.discord.executor;

import com.tdonuk.config.NewsAPIConfig;
import com.tdonuk.constant.Globals;
import com.tdonuk.discord.COMMAND;
import com.tdonuk.dto.NewsResponseDTO;
import com.tdonuk.dto.ResponseDTO;
import com.tdonuk.http.RestClient;
import com.tdonuk.util.app.CommandUtils;
import com.tdonuk.util.discord.MessageUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.naming.OperationNotSupportedException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Fetch news from NewsAPI and return in a format of list to the channel that bot is being called
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NewsAPIExecutor extends AbstractMessageExecutor {
    private static NewsAPIExecutor executor;

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

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

        COMMAND command = CommandUtils.parseCommand(message);

        String commandText = message.replace(command.getName(), "").trim();

        switch (command) {
            case NEWS -> {
                if(Globals.HELP.equals(commandText) || Globals.EXAMPLE.equals(commandText)) {
                    messageEvent.getMessage().reply("Here is a tutorial\n" + MessageUtils.list(Globals.newsTutorial) + "\n").queue();
                    return;
                }

                NewsResponseDTO response = getNews();

                Map<String, String> listData = new HashMap<>();
                response.getResults().forEach(n -> {
                    listData.put(String.format("%s\t(%s)", MessageUtils.bold(n.getTitle()), MessageUtils.italic(n.getPubDate().substring(0, n.getPubDate().indexOf(" ")))), n.getDescription());
                });

                StringBuilder news = new StringBuilder("Here are the breaking news for TR");
                news.append("\n\n").append(MessageUtils.list(listData)).append("\n");
                news.append(MessageUtils.italic("source: newsdata.io"));
                messageEvent.getMessage().getChannel().sendMessage(news.toString()).queue();
            }
        }

        logger.exiting("NewsAPIExecutor", "execute");
    }

    private NewsResponseDTO getNews() throws Exception {
        String url = NewsAPIConfig.SOURCE_URL.replaceAll("%API_KEY%", NewsAPIConfig.NEWS_API_KEY);

        ResponseDTO<NewsResponseDTO> newsResponse = RestClient.get(url, null, NewsResponseDTO.class);

        return newsResponse.getData();
    }
}
