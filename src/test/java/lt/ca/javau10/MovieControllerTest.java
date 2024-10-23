package lt.ca.javau10;  // Declare the package for the test class

import org.bson.types.ObjectId;  // Import ObjectId class for MongoDB ObjectId
import org.junit.jupiter.api.BeforeEach;  // Import JUnit annotation for setup before each test
import org.junit.jupiter.api.Test;  // Import JUnit annotation for test methods
import org.mockito.InjectMocks;  // Import Mockito annotation for injecting mocks into a class
import org.mockito.Mock;  // Import Mockito annotation for creating mock objects
import org.mockito.MockitoAnnotations;  // Import Mockito for initializing mocks
import org.springframework.http.MediaType;  // Import MediaType class for setting HTTP content type
import org.springframework.test.web.servlet.MockMvc;  // Import MockMvc class for testing MVC controllers
import org.springframework.test.web.servlet.setup.MockMvcBuilders;  // Import builder for MockMvc

import java.util.Arrays;  // Import utility for working with arrays
import java.util.List;  // Import List interface
import java.util.Optional;  // Import Optional class for handling null values

import static org.mockito.Mockito.when;  // Import Mockito method for stubbing methods
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;  // Import method for building GET requests
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;  // Import method for content validation
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;  // Import method for JSON path validation
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;  // Import method for status validation
import static org.hamcrest.Matchers.*;  // Import Hamcrest matchers for assertions

class MovieControllerTest {  // Define the test class for MovieController

    private MockMvc mockMvc;  // Declare MockMvc instance for simulating HTTP requests

    @Mock  // Indicate that this is a mock instance of MovieService
    private MovieService movieService;

    @InjectMocks  // Indicate that mocks should be injected into this instance
    private MovieController movieController;  // Declare an instance of the MovieController to be tested

    @BeforeEach  // Annotation to run this method before each test
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();  // Set up MockMvc with the controller
    }

    @Test  // Annotation to mark this method as a test case
    void testGetMovies() throws Exception {  // Method to test getting all movies
        // Sample movie data with ObjectId
        Movie movie1 = new Movie(new ObjectId(), "tt123456", "Movie One", "2021-01-01",  // Create first Movie instance
                                 "https://trailerlink1.com", "https://poster1.com",  // Set trailer link and poster
                                 Arrays.asList("backdrop1.jpg", "backdrop2.jpg"),  // Set backdrops
                                 Arrays.asList("Action", "Adventure"), null);  // Set genres and reviews (null)

        Movie movie2 = new Movie(new ObjectId(), "tt654321", "Movie Two", "2022-02-02",  // Create second Movie instance
                                 "https://trailerlink2.com", "https://poster2.com",  // Set trailer link and poster
                                 Arrays.asList("backdrop3.jpg", "backdrop4.jpg"),  // Set backdrops
                                 Arrays.asList("Drama", "Thriller"), null);  // Set genres and reviews (null)

        // Creating the list of movies
        List<Movie> movies = Arrays.asList(movie1, movie2);  // Create a list containing the two movies

        // Mocking the service layer
        when(movieService.findAllMovies()).thenReturn(movies);  // Stub the service method to return the movie list

        // Perform GET request and validate the response
        mockMvc.perform(get("/api/v1/movies")  // Simulate a GET request to the specified endpoint
                        .contentType(MediaType.APPLICATION_JSON))  // Set the content type to JSON
                .andExpect(status().isOk())  // Expect a 200 OK status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Expect the content type to be JSON
                .andExpect(jsonPath("$", hasSize(2)))  // Expect the response array size to be 2
                .andExpect(jsonPath("$[0].imdbId", is("tt123456")))  // Validate the first movie's IMDb ID
                .andExpect(jsonPath("$[0].title", is("Movie One")))  // Validate the first movie's title
                .andExpect(jsonPath("$[0].releaseDate", is("2021-01-01")))  // Validate the first movie's release date
                .andExpect(jsonPath("$[0].trailerLink", is("https://trailerlink1.com")))  // Validate the first movie's trailer link
                .andExpect(jsonPath("$[0].poster", is("https://poster1.com")))  // Validate the first movie's poster
                .andExpect(jsonPath("$[0].genres", containsInAnyOrder("Action", "Adventure")))  // Validate the first movie's genres
                .andExpect(jsonPath("$[1].imdbId", is("tt654321")))  // Validate the second movie's IMDb ID
                .andExpect(jsonPath("$[1].title", is("Movie Two")))  // Validate the second movie's title
                .andExpect(jsonPath("$[1].releaseDate", is("2022-02-02")))  // Validate the second movie's release date
                .andExpect(jsonPath("$[1].trailerLink", is("https://trailerlink2.com")))  // Validate the second movie's trailer link
                .andExpect(jsonPath("$[1].poster", is("https://poster2.com")))  // Validate the second movie's poster
                .andExpect(jsonPath("$[1].genres", containsInAnyOrder("Drama", "Thriller")));  // Validate the second movie's genres
    }

    @Test  // Annotation to mark this method as a test case
    void testGetSingleMovie() throws Exception {  // Method to test getting a single movie
        // Sample movie data
        Movie movie = new Movie(new ObjectId(), "tt123456", "Movie One", "2021-01-01",  // Create a Movie instance
                                "https://trailerlink1.com", "https://poster1.com",  // Set trailer link and poster
                                Arrays.asList("backdrop1.jpg", "backdrop2.jpg"),  // Set backdrops
                                Arrays.asList("Action", "Adventure"), null);  // Set genres and reviews (null)

        // Mocking the service layer
        when(movieService.findMovieByImdbId("tt123456")).thenReturn(Optional.of(movie));  // Stub the service method to return the movie

        // Perform GET request and validate the response
        mockMvc.perform(get("/api/v1/movies/tt123456")  // Simulate a GET request for a single movie by IMDb ID
                        .contentType(MediaType.APPLICATION_JSON))  // Set the content type to JSON
                .andExpect(status().isOk())  // Expect a 200 OK status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Expect the content type to be JSON
                .andExpect(jsonPath("$.imdbId", is("tt123456")))  // Validate the movie's IMDb ID
                .andExpect(jsonPath("$.title", is("Movie One")))  // Validate the movie's title
                .andExpect(jsonPath("$.releaseDate", is("2021-01-01")))  // Validate the movie's release date
                .andExpect(jsonPath("$.trailerLink", is("https://trailerlink1.com")))  // Validate the movie's trailer link
                .andExpect(jsonPath("$.poster", is("https://poster1.com")))  // Validate the movie's poster
                .andExpect(jsonPath("$.genres", containsInAnyOrder("Action", "Adventure")));  // Validate the movie's genres
    }
}
