package com.tdonuk.discord.executor;

import com.tdonuk.config.MobalyticsConfig;
import com.tdonuk.constant.Globals;
import com.tdonuk.constant.lol.ROLE;
import com.tdonuk.discord.COMMAND;
import com.tdonuk.dto.CounterDTO;
import com.tdonuk.util.discord.MessageUtils;
import com.tdonuk.util.web.WebUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

        String commandText = message.replace(command.getName(), "").trim();

        switch(command) {
            case CT -> { // !ct jungle kha zix
                if(Globals.HELP.equals(commandText) || Globals.EXAMPLE.equals(commandText)) {
                    messageEvent.getMessage().reply("Here is a tutorial\n" + MessageUtils.list(Globals.mobalyticsTutorial) + "\n").queue();
                    return;
                }

                ROLE role = ROLE.valueOf(commandText.substring(0, commandText.indexOf(" ")).toUpperCase());
                String champ = commandText.replace(role.getName(), "").trim();

                List<CounterDTO> counters = getCounters(role, champ);

                Map<String, String> countersMap = new HashMap<>();
                counters.forEach(c -> countersMap.put(MessageUtils.italic_bold(c.getChamp()) + " (" + MessageUtils.italic(c.getPercent()+ " win rate)"), ""));

                StringBuilder counterList = new StringBuilder(String.format("Here is the counters of the %s", MessageUtils.italic_bold(role.getName() + " " + champ)));
                counterList.append("\n\n").append(MessageUtils.list(countersMap));
                counterList.append(MessageUtils.italic("source: app.mobalytics.gg"));

                messageEvent.getMessage().reply(counterList.toString()).queue();
            }
        }

        logger.exiting("NewsAPIExecutor", "execute");
    }

    private List<CounterDTO> getCounters(ROLE role, String champ) throws IOException {
        String url = MobalyticsConfig.COUNTERS_PATH.replaceAll("%ROLE%", role.getName()).replaceAll("%CHAMP%", champ.replaceAll(" ", "").toLowerCase());

        Document mobalytics = WebUtils.getDocument(url);
        Elements rows = WebUtils.get("div[class='m-1p8lnhy']", mobalytics);

        List<CounterDTO> counters = new ArrayList<>();

        rows.stream().forEach(row -> {
            try {
                String champName = row.selectFirst("p[class='m-6j974']").ownText();
                String percent = row.selectFirst("div[class='m-oft0zz'] span").ownText();

                if(!champName.toLowerCase().contains("overall")) counters.add(new CounterDTO(champName, percent));
            } catch (Exception e) {

            }
        });

        return counters;
    }
}
