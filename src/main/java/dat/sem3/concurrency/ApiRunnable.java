package dat.sem3.concurrency;

import dat.sem3.api.ApiReader;
import dat.sem3.dto.MovieDTO;

import java.io.IOException;
import java.util.List;

public class ApiRunnable implements Runnable {
    private final List<MovieDTO> list;
    private final List<MovieDTO> resList;
    private final int index;

    public ApiRunnable(int index, List<MovieDTO> list, List<MovieDTO> resList) {
        this.index = index;
        this.list = list;
        this.resList = resList;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        System.out.println("Runnable with index " + index + " started");
        ApiReader reader = ApiReader.getInstance();
        list.forEach(e -> {
            try {
                resList.add(reader.fetchExtraInfo(e));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        System.out.println("Vector size: " + resList.size());
        System.out.println("Runnable with index " + index + " ended after " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
