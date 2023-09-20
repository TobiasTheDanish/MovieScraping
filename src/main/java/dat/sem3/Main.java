package dat.sem3;

import dat.sem3.api.ApiReader;
import dat.sem3.concurrency.ApiRunnable;
import dat.sem3.dto.MovieDTO;
import dat.sem3.webscraping.WebScraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        try {
            List<MovieDTO> movies = WebScraper.scrapeMovies();
            Thread t = new Thread(() -> synchronousApiCalls(new ArrayList<>(movies)));
            t.start();
            multiThreadedApiCalls(movies);
            /*
            Thread.sleep(6000);
            System.out.println("main thread woke from sleep");
             */
            t.join();
            System.out.println("----- All done -----");
        } catch (IOException | InterruptedException  e) {
            e.printStackTrace();
        }
    }

    public static void synchronousApiCalls(List<MovieDTO> movies) {
        /*
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         */
        ApiReader apiReader = ApiReader.getInstance();

        long startTime = System.currentTimeMillis();
        System.out.println("Synchronous calls started");
        movies.forEach(movie -> {
            try {
                apiReader.fetchExtraInfo(movie);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Synchronous calls ended after " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static void multiThreadedApiCalls(List<MovieDTO> movies) {
        List<MovieDTO> resList = new Vector<>(movies.size());
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 5; i++) {
            List<MovieDTO> subList = movies.subList(i * 50, (i+1) * 50);

            executorService.submit(new ApiRunnable(i, subList, resList));
        }
    }
}
