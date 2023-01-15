package com.tdonuk.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;
import java.util.logging.Logger;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseUtils {
    protected static Logger logger = Logger.getGlobal();
    protected static ObjectMapper mapper = new ObjectMapper();

    public static String[] mapToArray(final Map<String, String> map) {
        logger.entering(BaseUtils.class.getName(), "mapToArray", map);

        if(MapUtils.isEmpty(map)) return new String[]{"empty", "header"};

        String[] httpHeaders = new String[map.size() * 2];

        int i = 0;
        for(String key : map.keySet()) {
            httpHeaders[i] = key;
            httpHeaders[i+1] = map.get(key);

            i += 2;
        }

        logger.exiting(BaseUtils.class.getName(), "mapToArray",httpHeaders);
        return httpHeaders;
    }
}
