package com.vk.bot.services.impl;

import com.vk.bot.services.HttpService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HttpServiceImpl implements HttpService {

    private static final String KEY = "3cf6030078118d0c2316b979dcf9dadaef1a9d4d790e10a34b1f5d039041e24a2d0b01261aac93f7fb4de";
    private static final String VERSION = "5.126";
    private static final String BASIC_URL = "https://api.vk.com/method/";

    private String sendRequest(URI uri) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    @Override
    public String request(String method, Map<String, String> params) throws IOException, InterruptedException {
        StringBuilder stringRequest = new StringBuilder().append(BASIC_URL);
        stringRequest.append(method);
        stringRequest.append("?");

        params.entrySet().forEach(e -> {
            stringRequest.append(e.getKey())
                    .append("=")
                    .append(e.getValue())
                    .append("&");
        });

        stringRequest.append("access_token=").append(KEY);
        stringRequest.append("&v=").append(VERSION);

        return sendRequest(URI.create(stringRequest.toString()
                .replace(" ", "%20")
                .replace("\n", "%0a")));
    }
}
