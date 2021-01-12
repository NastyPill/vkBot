package com.vk.bot.services.impl;

import com.vk.bot.datamodels.impl.MessagesSentMetric;
import com.vk.bot.datamodels.impl.ThreadsMetric;
import com.vk.bot.services.HttpService;
import com.vk.bot.services.MessageService;
import com.vk.bot.services.StartService;
import com.vk.bot.threads.ChatThread;
import com.vk.bot.threads.NotifierThread;
import com.vk.bot.threads.SaverThread;

import java.io.IOException;

public class StartServiceImpl implements StartService {

    private static final Integer MY_ID = 137463274;

    private Thread threadMetricsSaver;
    private Thread messageSentMetricsSaver;
    private Thread notifierThread;

    private ThreadsMetric threadsMetric;
    private MessagesSentMetric messagesSentMetric;

    @Override
    public void start() throws IOException, InterruptedException {
        initMetrics();
        startMetricsSavers();
        startNotifiers();

        int errorsCounter = 0;
        int id = 1;
        while (errorsCounter < 10) {
            id++;
            HttpService httpService = new HttpServiceImpl();
            MessageService messageService = new MessageServiceImpl(id, httpService, true);

            if (messageService.send("bot started").contains("\"response\":0")) {
                new ChatThread(messageService, threadsMetric, messagesSentMetric).start();
                threadsMetric.addStarted();
                errorsCounter = 0;
            } else {
                errorsCounter++;
            }
        }

    }

    private void startMetricsSavers() {
        threadMetricsSaver = new SaverThread("threadMetrics.txt", threadsMetric);
        messageSentMetricsSaver = new SaverThread("messageSentMetrics.txt", messagesSentMetric);
        threadMetricsSaver.start();
        messageSentMetricsSaver.start();
    }

    private void initMetrics() {
        threadsMetric = new ThreadsMetric();
        messagesSentMetric = new MessagesSentMetric();
    }

    private void startNotifiers() {
        notifierThread = new NotifierThread(new MessageServiceImpl(MY_ID, new HttpServiceImpl(), false), threadsMetric, messagesSentMetric);
        notifierThread.start();
    }
}
