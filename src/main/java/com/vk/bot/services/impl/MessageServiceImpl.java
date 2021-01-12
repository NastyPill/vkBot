package com.vk.bot.services.impl;

import com.vk.bot.services.HttpService;
import com.vk.bot.services.MessageService;
import lombok.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MessageServiceImpl implements MessageService {

    private static final Random random = new Random();

    private String peerId;

    private HttpService httpService;
    public MessageServiceImpl(@NonNull Integer peerId, @NonNull HttpService httpService, boolean isPeerId) {
        this.peerId = Integer.toString(isPeerId ? peerId + 2000000000 : peerId);
        this.httpService = httpService;
    }

    @Override
    public String send(String message) throws IOException, InterruptedException {
        Map<String, String> params = new HashMap<>();
        params.put("peer_id", peerId);
        params.put("random_id", Integer.toString(random.nextInt(Integer.MAX_VALUE)));
        params.put("message", message);
        return httpService.request("messages.send", params);
    }

    public Long getPeerId() {
        return Long.parseLong(peerId);
    }
}
