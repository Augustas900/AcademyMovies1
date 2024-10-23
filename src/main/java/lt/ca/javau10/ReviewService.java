package lt.ca.javau10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String imdbId) {
        // Create a new review
        Review review = new Review(reviewBody, LocalDateTime.now(), LocalDateTime.now());
        
        // Insert the review into the Review collection
        repository.insert(review);
        
        // Update the movie document by pushing the complete review object into the reviews array
        mongoTemplate.update(Movie.class)
            .matching(Criteria.where("imdbId").is(imdbId))
            .apply(new Update().push("reviews").value(review)) // Store the entire review object
            .first();
        
        return review;
    }
}