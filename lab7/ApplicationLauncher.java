package ru.izebit.seventh;

import java.util.concurrent.TimeUnit;


public class ApplicationLauncher {
    public static void main(String[] args) throws Exception {
//        if (args.length != 2)
//            throw new IllegalArgumentException(
//                    "You should pass 2 parameters. " +
//                            "The first of them is host, the second one is depth");
//
//        String host = args[0];
//        int depth = Integer.parseInt(args[1]);


        final WebCrawler webCrawler = new WebCrawler("http://habr.ru", 1, 4);
        webCrawler.visit();

        TimeUnit.DAYS.sleep(1);
    }
}
