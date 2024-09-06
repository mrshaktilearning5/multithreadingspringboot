package services;

import dto.Review;
import org.springframework.stereotype.Service;

@Service
public class ReviewServices {


    public Review retrieveReviews(String productId) throws InterruptedException {
        Thread.sleep(1000);

        return new Review(200,4);
    }
}
