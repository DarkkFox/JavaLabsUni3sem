package ru.izebit.sixth;

/**
 * @author <a href="mailto:izebit@gmail.com">Artem Konovalov</a> <br/>
 * Date: 24.10.2019
 */
public class ApplicationLauncher {
    public static void main(String[] args) {
//        if (args.length != 2)
//            throw new IllegalArgumentException(
//                    "You should pass 2 parameters. " +
//                            "The first of them is host, the second one is depth");
//
//        String host = args[0];
//        int depth = Integer.parseInt(args[1]);


        final WebCrawler webCrawler = new WebCrawler("http://habr.ru", 1);
        webCrawler.visit();
    }
}
