package com.tdonuk.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.collections4.MapUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class HttpUtils extends BaseUtils{
    public static String createRequestParams(final Map<String, String> paramsMap, final String url) {
        if(MapUtils.isEmpty(paramsMap)) return url;

        return paramsMap.keySet().stream().map(key -> key+"="+paramsMap.get(key)).collect(Collectors.joining("&", url+"?", "")).replaceAll(" ", "%20");
    }

    public static URI createURI(String base, Map<String, String> params) {
        return URI.create(createRequestParams(params, base));
    }

    public static HttpRequest createRequest(final String url, Map<String, String> params, Map<String,String> headers, final Object body, final String method) throws JsonProcessingException {
        return HttpRequest.newBuilder(createURI(url, params))
                .headers(mapToArray(headers))
                .method(method.toUpperCase(), Objects.isNull(body) ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body)))
                .timeout(Duration.ofSeconds(10))
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public static HttpRequest get(final String url, Map<String, String> params, Map<String, String> headers) throws JsonProcessingException {
        return createRequest(url, params, headers, null, "GET");
    }

    public static HttpRequest get(final String url, Map<String, String> params) throws JsonProcessingException {
        return createRequest(url, params, null, null, "GET");
    }

}
