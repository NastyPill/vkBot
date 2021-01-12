package com.vk.bot.services;

import java.io.IOException;

public interface MessageService {

    public String send(String message) throws IOException, InterruptedException;

    Long getPeerId();

}
