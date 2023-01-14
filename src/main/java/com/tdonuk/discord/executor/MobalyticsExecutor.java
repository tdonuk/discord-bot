package com.tdonuk.discord.executor;

import com.tdonuk.config.MobalyticsConfig;
import com.tdonuk.constant.lol.ROLE;
import com.tdonuk.discord.COMMAND;
import com.tdonuk.http.dto.Counter;
import com.tdonuk.util.discord.MessageUtils;
import com.tdonuk.util.web.WebUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.util.*;

/**
 * Fetch data from mobalytics like CT's of a champion, synergies, builds etc..
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MobalyticsExecutor extends AbstractMessageExecutor {
    private static MobalyticsExecutor executor;

    public static MobalyticsExecutor instance() {
        if(Objects.isNull(executor)) executor = new MobalyticsExecutor();

        return executor;
    }

    @Override
    public void execute(GenericEvent event) throws Exception {
        logger.entering(this.getClass().getName(), "execute", event);

        MessageReceivedEvent messageEvent = (event instanceof MessageReceivedEvent) ? (MessageReceivedEvent) event : null; // currently not supporting event other than message

        if(Objects.isNull(messageEvent)) throw new OperationNotSupportedException("This operation is not supported currently");

        String message = ((MessageReceivedEvent) event).getMessage().getContentDisplay();

        COMMAND command = COMMAND.byName(message.substring(0,message.indexOf(" ")));

        switch(command) {
            case CT -> { // !ct jungle kha zix
                String[] query = message.substring(command.getName().length()+1).split(" "); // jungle | kha zix
                ROLE role = ROLE.valueOf(query[0].toUpperCase());
                String champ = message.substring(message.indexOf(" ", message.indexOf(role.getName()))).replaceAll(" ", "");

                List<Counter> counters = getCounters(role, champ);

                Map<String, String> countersMap = new HashMap<>();
                Collections.sort(counters, Comparator.comparingDouble(c -> Double.valueOf(c.getPercent().replaceAll("[^0-9.]", ""))));
                counters.forEach(c -> countersMap.put(c.getChamp(), c.getPercent() + " win rate"));

                messageEvent.getMessage().reply(MessageUtils.singleRowList(String.format("Here is the counters of the %s", MessageUtils.italic_bold(role.getName() + " " + champ)), countersMap)).queue();
            }
            case SY -> {
                throw new OperationNotSupportedException("This feature is not supported currently");
            }
        }

        logger.exiting("NewsAPIExecutor", "execute");
    }

    private List<Counter> getCounters(ROLE role, String champ) throws IOException {
        String url = MobalyticsConfig.COUNTERS_PATH.replaceAll("%ROLE%", role.getName()).replaceAll("%CHAMP%", champ.toLowerCase());

        Document mobalytics = WebUtils.getDocument(url);
        Elements rows = WebUtils.get("div[class='m-1p8lnhy']", mobalytics);

        List<Counter> counters = new ArrayList<>();

        rows.stream().forEach(row -> {
            try {
                String champName = row.selectFirst("p[class='m-6j974']").ownText();
                String percent = row.selectFirst("div[class='m-oft0zz'] span").ownText();

                if(!champName.toLowerCase().contains("overall")) counters.add(new Counter(champName, percent));
            } catch (Exception e) {

            }
        });

        return counters;
    }
}
