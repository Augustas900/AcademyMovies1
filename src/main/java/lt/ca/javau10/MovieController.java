package lt.ca.javau10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    // GET endpoint to retrieve all movies
    @GetMapping
    public ResponseEntity<List<Movie>> getMovies() {
        return new ResponseEntity<List<Movie>>(service.findAllMovies(), HttpStatus.OK);
    }

    // GET endpoint to retrieve a single movie by IMDb ID
    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Movie>> getSingleMovie(@PathVariable String imdbId) {
        return new ResponseEntity<Optional<Movie>>(service.findMovieByImdbId(imdbId), HttpStatus.OK);
    }

    // POST endpoint to create a new movie
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie createdMovie = service.saveMovie(movie); // Assuming a saveMovie method exists in the service
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }
}
