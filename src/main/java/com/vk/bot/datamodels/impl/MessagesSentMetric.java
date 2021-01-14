package com.vk.bot.datamodels.impl;

import com.vk.bot.datamodels.Metric;

import java.util.Map;
import java.util.TreeMap;

public class MessagesSentMetric implements Metric {

    private volatile Long total;
    private Map<Long, Long> sentForEveryThread;
    private Long start;

    public MessagesSentMetric() {
        this.sentForEveryThread = new TreeMap<>();
        this.total = 0L;
        this.start = System.currentTimeMillis();
    }

    @Override
    public String getData() {
        Long currentTime = System.currentTimeMillis();
        StringBuilder result = new StringBuilder();
        result.append("total: ").append(total);
        for (Map.Entry e : sentForEveryThread.entrySet()) {
            result.append("\n")
                    .append("For id: ")
                    .append(e.getKey())
                    .append(" messages sent: ")
                    .append(e.getValue());
        }
        result.append("\nAverage per sec: ")
                .append(Double.valueOf(total) / ((currentTime - start) / 1000))
                .append(" for ")
                .append(sentForEveryThread.size())
                .append(" threads");
        return result.toString();
    }

    synchronized
    public void plusMessage(Long id) {
        sentForEveryThread.put(id, sentForEveryThread.getOrDefault(id, 0L) + 1);
        total++;
    }

}
