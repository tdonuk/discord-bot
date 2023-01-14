package com.tdonuk.constant.lol;

public enum ROLE {
    TOP("top"), JUNGLE("jungle"), MID("mid"), BOT("bot"), SUPP("supp");

    private final String name;

    ROLE(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
