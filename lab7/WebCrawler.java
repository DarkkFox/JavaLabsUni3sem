package ru.izebit.seventh;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;


public class WebCrawler {
    private final URLPool pool;
    private final int threadCount;
    private final int depth;

    public WebCrawler(final String host,
                      final int depth,
                      final int threadCount) {
        this.depth = depth;

        this.threadCount = threadCount;
        URL startHost = CrawlerTask.getCanonicalUrl(host);
        if (startHost == null)
            throw new IllegalArgumentException("host is not valid");
        if (depth < 0)
            throw new IllegalArgumentException("depth should be bigger than zero");

        pool = new URLPool();
        pool.addUrl(startHost, 0);
    }


    public void visit() {
        final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        IntStream
                .range(0, threadCount)
                .mapToObj(e -> new CrawlerTask(this.pool, this.depth))
                .forEach(forkJoinPool::execute);
    }


}
