package com.tdonuk.util.discord;

import com.tdonuk.config.JDAConfig;
import com.tdonuk.constant.Globals;
import com.tdonuk.util.BaseUtils;
import org.jsoup.internal.StringUtil;

import java.util.Map;

public final class MessageUtils extends BaseUtils {
    public static String bold(String msg) {
        return String.format("**%s**", msg);
    }

    public static String italic(String msg) {
        return String.format("*%s*", msg);
    }

    public static String underlined(String msg) {
        return String.format("__%s__", msg);
    }

    public static String strikeThrough(String msg) {
        return String.format("~~%s~~", msg);
    }

    public static String italic_bold(String msg) {
        return italic(bold(msg));
    }

    public static String italic_bold_underlined(String msg) {
        return underlined(italic(bold(msg)));
    }

    public static String italic_underlined(String msg) {
        return underlined(italic(msg));
    }

    /**
     * Writes the given data in a list format
     */
    public static String list(Map<String, String> data) {
        StringBuilder list = new StringBuilder();

        int row = 0;
        int length;
        for(String key : data.keySet()) {
            row++;

            length = key.length() + data.get(key).length();
            if(list.length() + length > JDAConfig.MAX_MESSAGE_LENGTH) break;

            String value = data.get(key);

            list.append(String.format("%s\n%s\n", key, value));

            if(!StringUtil.isBlank(value)) list.append("\n"); // som prettifying
        }

        return list.toString();
    }

    public static String singleRowList(String header, Map<String, String> data) {
        StringBuilder list = new StringBuilder(header+"\n");

        int row = 0;
        for(String key : data.keySet()) {
            list.append(String.format("%d - %s (%s)\n", ++row, italic_bold(key), italic(data.get(key))));
        }

        return list.toString();
    }
}
