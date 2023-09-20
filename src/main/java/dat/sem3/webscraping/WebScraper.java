package dat.sem3.webscraping;

import dat.sem3.dto.MovieDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WebScraper {

    public static List<MovieDTO> scrapeMovies() throws IOException {
        List<MovieDTO> res = new ArrayList<>();
        Document dom = Jsoup.connect("https://www.imdb.com/chart/top/?ref_=nv_mv_250").get();

        dom.select("li.ipc-metadata-list-summary-item").forEach(element -> {
                    Element titleElem = element.select("div.ipc-title").first();

                    String title = "";
                    String imdbId = "";
                    if (titleElem != null) {
                        title = titleElem.text().split("\\.", 2)[1].trim();
                        imdbId = titleElem.select("a").first().attr("href").split("/")[2];
                    }
                    String imgUrl = element.select("img.ipc-image").attr("src");

                    Elements metadata = element.select("span.cli-title-metadata-item");
                    int releaseYear = Integer.parseInt(metadata.get(0).text());
                    Duration duration = Duration.parse("PT"+metadata.get(1).text().replace(" ", "").toUpperCase());
                    String mpaaRating = metadata.get(2).text();

                    String[] ratingData = element.select("span.ipc-rating-star").text().split(" ");
                    double rating = Double.parseDouble(ratingData[0]);
                    long numRatings = convertNumOfRatingsStr(ratingData[1]);

                    res.add(MovieDTO.builder()
                                    .imdbId(imdbId)
                                    .title(title)
                                    .thumbnailUrl(imgUrl)
                                    .releaseYear(releaseYear)
                                    .duration(duration)
                                    .mpaaRating(mpaaRating)
                                    .rating(rating)
                                    .numberOfRatings(numRatings)
                                    .build());
                });

        return res;
    }

    private static long convertNumOfRatingsStr(String numOfRatingsStr) {
        if (numOfRatingsStr.contains("K")) {
            String str = numOfRatingsStr.replaceAll("([K()])", "");
            if (!str.contains(".")) {
                return Long.parseLong(str + "000");
            } else {
                return Long.parseLong(str.replace(".", "") + "00");
            }
        } else if (numOfRatingsStr.contains("M")) {
            String str = numOfRatingsStr.replaceAll("([M()])", "");
            if (!str.contains(".")) {
                return Long.parseLong(str + "000000");
            } else {
                return Long.parseLong(str.replace(".", "") + "00000");
            }
        }

        try {
            return Long.parseLong(numOfRatingsStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
