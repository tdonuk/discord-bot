package com.tdonuk.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdonuk.http.dto.ResponseDTO;
import com.tdonuk.util.HttpUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestClient {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();

    public static <T> ResponseDTO<T> exchange(String url, String method, Object body, Map<String, String> headers, Map<String,String> params, Class<T> clazz) throws Exception {
        HttpRequest request = HttpUtils.createRequest(url, params, headers, body, method.toUpperCase());

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return new ResponseDTO<T>(mapper.readValue(response.body(), clazz));
    }

    public static  <T> ResponseDTO<T> get(String url, Map<String, String> headers, Map<String,String> params, Class<T> clazz) throws Exception {
        return exchange(url, "GET", null, headers, params, clazz);
    }

    public static  <T> ResponseDTO<T> get(String url, Map<String,String> params, Class<T> clazz) throws Exception {
        return exchange(url, "GET", null, null, params, clazz);
    }

}
