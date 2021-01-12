package com.vk.bot.threads;

import com.vk.bot.datamodels.impl.MessagesSentMetric;
import com.vk.bot.datamodels.impl.ThreadsMetric;
import com.vk.bot.services.MessageService;

import java.io.IOException;

public class ChatThread extends Thread {

    private MessageService messageService;
    private ThreadsMetric threadsMetric;
    private MessagesSentMetric messagesSentMetric;

    public ChatThread(MessageService messageService, ThreadsMetric threadsMetric, MessagesSentMetric messagesSentMetric) {
        this.messageService = messageService;
        this.threadsMetric = threadsMetric;
        this.messagesSentMetric = messagesSentMetric;
    }

    @Override
    public void run() {
        int errorsCounter = 0;
        while (errorsCounter < 10 && !this.isInterrupted()) {
            try {
                if(!messageService.send("check").contains("\"response\":")) {
                    errorsCounter++;
                    System.out.println(errorsCounter + " errors in thread");
                } else {
                    errorsCounter = 0;
                    messagesSentMetric.plusMessage(messageService.getPeerId());
                }
                sleep(1000);
            } catch (IOException | InterruptedException e) {
                errorsCounter++;
            };
        }
        threadsMetric.removeAlive();
    }
}
