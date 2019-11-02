package ru.izebit.sixth;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WebCrawler {
    private final Set<URL> visitedHosts = new HashSet<>();
    private final Queue<URLEntry> queue = new LinkedList<URLEntry>();
    private final URL startHost;
    private final int depth;

    public WebCrawler(final String host,
                      final int depth) {
        this.startHost = getCanonicalUrl(host);
        if (startHost == null)
            throw new IllegalArgumentException("host is not valid");
        this.depth = depth;
        if (this.depth < 0)
            throw new IllegalArgumentException("depth should be bigger than zero");
    }


    public Set<URL> visit() {
        queue.add(new URLEntry(0, startHost));

        while (!queue.isEmpty()) {
            URLEntry entry = queue.poll();
            if (entry.depth > depth)
                continue;

            URL url = entry.url;
            if (visitedHosts.contains(url))
                continue;

            queue.addAll(getUrlsFrom(readContent(url), entry.depth + 1));
            visitedHosts.add(url);
            System.out.println(url);
        }

        return visitedHosts;
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

    private static URL getCanonicalUrl(String host) {
        try {
            if (host.startsWith("http") || host.startsWith("https"))
                return new URL(host);
            else
                return new URL("http://" + host);
        } catch (Exception e) {
            return null;
        }
    }

    @AllArgsConstructor
    private static class URLEntry {
        private final int depth;
        private final URL url;
    }
}
