package com.tdonuk.util.app;

import com.tdonuk.discord.COMMAND;
import com.tdonuk.util.BaseUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommandUtils extends BaseUtils {
    public static COMMAND parseCommand(String msg) throws Exception {
        for(COMMAND cmd : COMMAND.values()) {
            if(msg.startsWith(cmd.getName())) return cmd;
        }
        throw new Exception("Can't parse command: " + msg);
    }
}
