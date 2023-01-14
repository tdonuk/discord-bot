package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdonuk.util.BaseUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UtilTests {
    private Map<String, String> headersMap = new HashMap<>(), params = new HashMap<>();
    private String url, method, requestBody;

    private ObjectMapper mapper;

    @Before
    public void init() {
        headersMap.put("X-Dummy-Header-1", "Dummy1");
        headersMap.put("X-Dummy-Header-2", "Dummy2");

        params.put("dummy-param", "dummy");

        url = "dummy.com";
        method = "GET";

        requestBody = null;

        mapper = new ObjectMapper();
    }

    @Test
    public void canConvertHeaders() {
        String[] httpHeaders = BaseUtils.mapToArray(headersMap);

        Assert.assertEquals(httpHeaders.length, headersMap.size() * 2);

        System.out.println(Arrays.toString(httpHeaders));
    }

}
