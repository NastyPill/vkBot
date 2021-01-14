package com.vk.bot.threads;

import com.vk.bot.datamodels.Metric;

import java.io.*;

public class SaverThread extends Thread {

    private String path;
    private Metric metric;

    public SaverThread(String path, Metric metric) {
        this.path = path;
        this.metric = metric;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        int errorsCounter = 0;
        File file = new File(path);
        while (errorsCounter < 10 && !this.isInterrupted())
        try {
            sleep(5_000L);
            file.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bufferedWriter.write(metric.getData());
            bufferedWriter.close();
        } catch (InterruptedException | IOException e) {
            errorsCounter++;
            e.printStackTrace();
        }
    }


}
