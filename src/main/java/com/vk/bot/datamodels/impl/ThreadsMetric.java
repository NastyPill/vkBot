package com.vk.bot.datamodels.impl;

import com.vk.bot.datamodels.Metric;

public class ThreadsMetric implements Metric {

    private volatile Long alive;
    private volatile Long started;

    public ThreadsMetric() {
        this.alive = 0L;
        this.started = 0L;
    }

    @Override
    public String getData() {
        return "Alive: " + alive + "\nStarted: " + started;
    }

    public Long getAlive() {
        return alive;
    }

    public synchronized void removeAlive() {
        alive--;
    }

    public Long getStarted() {
        return started;
    }

    public synchronized void addStarted() {
        started++;
        alive++;
    }
}
