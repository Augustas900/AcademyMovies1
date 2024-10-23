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

import java.time.LocalDateTime;  // Import LocalDateTime class for date and time handling
import java.util.HashMap;  // Import HashMap for using hash maps
import java.util.Map;  // Import Map interface for using maps

import static org.mockito.Mockito.when;  // Import Mockito method for stubbing methods
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;  // Import method for building POST requests
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;  // Import method for content validation
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;  // Import method for JSON path validation
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;  // Import method for status validation
import static org.hamcrest.Matchers.is;  // Import Hamcrest matchers for assertions

class ReviewControllerTest {  // Define the test class for ReviewController

    private MockMvc mockMvc;  // Declare MockMvc instance for simulating HTTP requests

    @Mock  // Indicate that this is a mock instance of ReviewService
    private ReviewService reviewService;

    @InjectMocks  // Indicate that mocks should be injected into this instance
    private ReviewController reviewController;  // Declare an instance of the ReviewController to be tested

    @BeforeEach  // Annotation to run this method before each test
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();  // Set up MockMvc with the controller
    }

    @Test  // Annotation to mark this method as a test case
    void testCreateReview() throws Exception {  // Method to test creating a new review
        // Sample review data
        Review review = new Review(new ObjectId(), "Great movie!", LocalDateTime.now(), LocalDateTime.now());  // Create a new Review instance

        // Mocking the service layer
        when(reviewService.createReview("Great movie!", "tt123456")).thenReturn(review);  // Stub the service method to return the review instance

        // JSON request body
        Map<String, String> payload = new HashMap<>();  // Create a new HashMap to hold the request data
        payload.put("reviewBody", "Great movie!");  // Add review body to the map
        payload.put("imdbId", "tt123456");  // Add IMDb ID to the map

        // Perform POST request and validate the response
        mockMvc.perform(post("/api/v1/reviews")  // Simulate a POST request to the specified endpoint
                        .contentType(MediaType.APPLICATION_JSON)  // Set the content type to JSON
                        .content("{\"reviewBody\": \"Great movie!\", \"imdbId\": \"tt123456\"}"))  // Set the JSON content for the request
                .andExpect(status().isOk())  // Expect a 200 OK status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Expect the content type to be JSON
                .andExpect(jsonPath("$.body", is("Great movie!")));  // Validate that the response body matches the expected review body
    }
}
