package dat.sem3.model;

import dat.sem3.dto.MovieDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "movies")
@Entity
public class MovieEntity {
    @Id
    @Column(name = "movie_id")
    private String imdbId;

    @Column(name = "movie_title")
    private String title;

    @Column(name = "movie_img_url")
    private String thumbnailUrl;

    @Column(name = "movie_rating")
    private double rating;

    @Column(name = "movie_num_of_ratings")
    private long numberOfRatings;

    @Column(name = "movie_release_year")
    private int releaseYear;

    @Column(name = "movie_mpaa_rating")
    private String mpaaRating;

    @Column(name = "movie_duration")
    private Duration duration;

    @Column(name = "movie_overview")
    private String overview;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "movie_release_date")
    private LocalDate releaseDate;

    public MovieEntity(MovieDTO dto) {
        this.imdbId = dto.getImdbId();
        this.title = dto.getTitle();
        this.thumbnailUrl = dto.getThumbnailUrl();
        this.rating = dto.getRating();
        this.numberOfRatings = dto.getNumberOfRatings();
        this.releaseYear = dto.getReleaseYear();
        this.mpaaRating = dto.getMpaaRating();
        this.duration = dto.getDuration();
        this.overview = dto.getOverview();
        this.releaseDate = dto.getReleaseDate();
    }
}
