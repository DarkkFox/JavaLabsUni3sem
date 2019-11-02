package ru.izebit.seventh;

import lombok.SneakyThrows;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;


public class URLPool {
    private final Queue<URLEntry> queue = new LinkedList<>();
    private Set<URL> visitedUrls = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public void addUrl(URL url, int depth) {
        if (!visitedUrls.contains(url))
            synchronized (this) {
                queue.add(new URLEntry(depth, url));
                notifyAll();
            }
    }

    @SneakyThrows
    public URLEntry getNextUrl() {
        synchronized (this) {
            while (!Thread.currentThread().isInterrupted()) {
                final URLEntry entry = queue.poll();
                if (entry == null)
                    wait();
                else
                    return entry;
            }
        }
        return null;
    }

    public void addVisitedUrl(final URLEntry entry) {
        System.out.printf("%s : %s\n", entry.getDepth(), entry.getUrl());
        visitedUrls.add(entry.getUrl());
    }
}
