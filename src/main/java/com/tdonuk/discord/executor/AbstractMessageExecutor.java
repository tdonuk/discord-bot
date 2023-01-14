package com.tdonuk.discord.executor;

import java.util.logging.Logger;

public abstract class AbstractMessageExecutor implements MessageExecutor {
    protected static Logger logger = Logger.getGlobal();
    protected static MessageExecutor instance;
}
