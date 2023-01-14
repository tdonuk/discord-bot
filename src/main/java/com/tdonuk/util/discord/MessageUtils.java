package com.tdonuk.util.discord;

import com.tdonuk.util.BaseUtils;

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
        for(String key : data.keySet()) {
            list.append(String.format("%d - %s", ++row, italic_bold(key)));
            list.append("\n\t").append(data.get(key));
        }

        return list.toString();
    }
}
