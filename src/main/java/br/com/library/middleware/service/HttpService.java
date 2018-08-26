package br.com.library.middleware.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class HttpService {

    private final Logger log = LoggerFactory.getLogger(HttpService.class);

    private final OkHttpClient client;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public HttpService() {
        this.client = new OkHttpClient();
    }

    public String doGetRequest(String url, String token) {
        Request request;
        if (StringUtils.hasText(token)) {
            request = new Request.Builder()
                .header("Authorization", token)
                .url(url)
                .build();
        } else {
            request = new Request.Builder()
                .url(url)
                .build();
        }

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                log.debug("Request successful to '{}'", url);
                return response.body().string();
            }
            log.warn("Error in response of '{}'", url);
            return null;
        } catch (IOException e) {
            log.warn("Error in request to '{}': {}", url, e.getMessage());
            return null;
        }
    }

    public String doPostRequest(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
            .url(url)
            .post(body)
            .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                log.debug("Request successful to '{}'", url);
                return response.body().string();
            }
            log.warn("Error in response of '{}'", url);
            return null;
        } catch (IOException e) {
            log.warn("Error in request to '{}': {}", url, e.getMessage());
            return null;
        }
    }

}
