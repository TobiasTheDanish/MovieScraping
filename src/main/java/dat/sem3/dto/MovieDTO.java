package dat.sem3.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDate;

@Getter
@ToString
public class MovieDTO {
    private final String imdbId;
    private final String title;
    private final String thumbnailUrl;
    private final double rating;
    private final long numberOfRatings;
    private final int releaseYear;
    private final String mpaaRating;
    private final Duration duration;
    @Setter
    private String overview;
    @Setter
    private LocalDate releaseDate;

    @Builder
    public MovieDTO(String imdbId, String title, String thumbnailUrl, double rating, long numberOfRatings, int releaseYear, String mpaaRating, Duration duration) {
        this.imdbId = imdbId;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
        this.releaseYear = releaseYear;
        this.mpaaRating = mpaaRating;
        this.duration = duration;
    }
}
