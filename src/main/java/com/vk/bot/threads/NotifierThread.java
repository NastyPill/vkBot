package com.vk.bot.threads;

import com.vk.bot.datamodels.Metric;
import com.vk.bot.services.MessageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotifierThread extends Thread {

    private MessageService messageService;
    private List<Metric> metricsList;

    public NotifierThread(MessageService messageService, Metric... metrics) {
        this.messageService = messageService;
        this.metricsList = new ArrayList(Arrays.asList(metrics));
    }

    public NotifierThread(MessageService messageService, List<Metric> metricsList) {
        this.messageService = messageService;
        this.metricsList = metricsList;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                for (Metric item : metricsList) {
                    messageService.send(item.getData());
                }
                sleep(60_000L);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                new NotifierThread(messageService, metricsList).start();
                this.interrupt();
            }
        }
    }
}
