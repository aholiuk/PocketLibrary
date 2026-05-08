package ch.holiuk.anna.pocket_library.review;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping
  public Review createReview(@AuthenticationPrincipal Jwt jwt,
                             @RequestParam Long bookId,
                             @RequestBody String text) {

    String userId = jwt.getSubject();

    return reviewService.createReview(userId, bookId, text);
  }

  @GetMapping
  public List<Review> getAllReviews() {
    return reviewService.getAllReviews();
  }

  @GetMapping("/book/{bookId}")
  @Validated
  public List<Review> getReviewsByBook(@PathVariable Long bookId) {
    return reviewService.getReviewsByBook(bookId);
  }

  @GetMapping("/user/{userId}")
  public List<Review> getReviewsByUser(@PathVariable String userId) {
    return reviewService.getReviewsByUser(userId);
  }
}

//TODO: Assign roles to all the endpoints