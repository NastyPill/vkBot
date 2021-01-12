package com.vk.bot.services;

import java.io.IOException;
import java.util.Map;

public interface HttpService {

    String request(String method, Map<String, String> params) throws IOException, InterruptedException;
}
