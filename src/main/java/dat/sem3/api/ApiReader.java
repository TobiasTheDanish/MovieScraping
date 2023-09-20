package dat.sem3.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dat.sem3.dto.MovieDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDate;

public class ApiReader {
    private final String apiKey = "7508415a3efd22b77ed6c787432502eb";
    private final String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3NTA4NDE1YTNlZmQyMmI3N2VkNmM3ODc0MzI1MDJlYiIsInN1YiI6IjY1MGI1NDJmY2FkYjZiMDBhYmM2YjY3NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.3bgpWgIBh-7eCFlLg-QJ1Kh4uvvYBOpTaoQlplWCQdg";
    private final String baseUrl = "https://api.themoviedb.org/3";
    private final Gson gson;
    private static ApiReader instance;

    private ApiReader() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public static ApiReader getInstance() {
        if (instance == null) {
            instance = new ApiReader();
        }

        return instance;
    }

    public MovieDTO fetchExtraInfo(MovieDTO m) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(baseUrl + "/find/" + m.getImdbId() + "?external_source=imdb_id")
                .get()
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            JsonObject res = gson.fromJson(response.body().string(), JsonObject.class);

            res = res.getAsJsonArray("movie_results").get(0).getAsJsonObject();
            m.setOverview(res.get("overview").getAsString());
            m.setReleaseDate(LocalDate.parse(res.get("release_date").getAsString()));
        }

        return m;
    }
}
