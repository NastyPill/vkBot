package com.vk.bot;

import com.vk.bot.services.StartService;
import com.vk.bot.services.impl.StartServiceImpl;

import java.io.IOException;
import java.util.Random;

public class Main {

    private static String key = "7fc9c54786ec1535ab1e17a7b76b9781d166dfd56ec68f69d17cc84968149b080779294db87610ee720d8";
    private static Random random = new Random();

    public static void main(String[] args) throws IOException, InterruptedException {
        StartService startService = new StartServiceImpl();
        startService.start();
    }
}
