package ru.izebit.seventh;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CrawlerTask implements Runnable {
    private final URLPool pool;
    private final int depth;

    private volatile boolean isWorking = true;

    public CrawlerTask(final URLPool pool, int depth) {
        this.pool = pool;
        this.depth = depth;
    }

    public void stop() {
        isWorking = false;
    }

    @Override
    public void run() {
        while (isWorking) {
            URLEntry urlEntry = pool.getNextUrl();
            if (urlEntry == null)
                return;

            if (urlEntry.getDepth() > depth)
                continue;

            pool.addVisitedUrl(urlEntry);
            if (urlEntry.getDepth() + 1 <= depth) {
                final Collection<URLEntry> newUrls = getUrlsFrom(readContent(urlEntry.getUrl()), urlEntry.getDepth() + 1);
                for (URLEntry entry : newUrls)
                    pool.addUrl(entry.getUrl(), entry.getDepth());
            }
        }
    }

    @SneakyThrows
    private static String readContent(URL url) {
        StringBuilder content = new StringBuilder();

        HttpURLConnection httpURLConnection = fetchURL(url);
        if (httpURLConnection == null)
            return null;

        httpURLConnection.setInstanceFollowRedirects(true);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                content.append(inputLine).append("\n");

        } catch (Exception e) {
            return null;
        } finally {
            httpURLConnection.disconnect();
        }

        return content.toString();
    }

    @SneakyThrows
    private static HttpURLConnection fetchURL(URL dest) {
        HttpURLConnection yc = (HttpURLConnection) dest.openConnection();
        yc.setInstanceFollowRedirects(false);
        yc.setUseCaches(false);
        int responseCode = yc.getResponseCode();
        if (responseCode >= 300 && responseCode < 400) { // brute force check, far too wide
            URL url = getCanonicalUrl(yc.getHeaderField("Location"));
            if (url == null)
                return null;
            else
                return fetchURL(url);
        }
        return yc;
    }

    private static Collection<URLEntry> getUrlsFrom(String content, int depth) {
        if (content == null || content.isEmpty())
            return Collections.emptySet();

        List<URLEntry> urls = new ArrayList<>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?+-=\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(content);

        while (urlMatcher.find()) {
            String href = content.substring(urlMatcher.start(0), urlMatcher.end(0));
            URL url = getCanonicalUrl(href);
            if (url != null)
                urls.add(new URLEntry(depth, url));
        }

        return urls;
    }

    public static URL getCanonicalUrl(String host) {
        try {
            if (host.startsWith("http") || host.startsWith("https"))
                return new URL(host);
            else
                return new URL("http://" + host);
        } catch (Exception e) {
            return null;
        }
    }
}
