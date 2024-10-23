package lt.ca.javau10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    // Retrieve all movies
    public List<Movie> findAllMovies() {
        return repository.findAll();
    }

    // Retrieve a single movie by its IMDb ID
    public Optional<Movie> findMovieByImdbId(String imdbId) {
        return repository.findMovieByImdbId(imdbId);
    }

    // Save a new movie
    public Movie saveMovie(Movie movie) {
        return repository.save(movie);
    }
}
