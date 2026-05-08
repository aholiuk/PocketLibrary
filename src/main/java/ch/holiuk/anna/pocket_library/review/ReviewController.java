package ch.holiuk.anna.pocket_library.review;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="Review", description="Manage reviews")
@RequestMapping("/reviews")
public class ReviewController {

  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @Tag(name="Post Review", description="Create new review for the")
  @PostMapping
  public Review createReview(@AuthenticationPrincipal Jwt jwt,
                             @RequestParam Long bookId,
                             @RequestBody String text) {

    String userId = jwt.getSubject();

    return reviewService.createReview(userId, bookId, text);
  }

  @Tag(name="Get All Reviews", description="Receive all the reviews")
  @GetMapping
  public List<Review> getAllReviews() {
    return reviewService.getAllReviews();
  }

  @Tag(name="Get All Reviews By Book", description="Receive all the reviews for one book by bookId")
  @GetMapping("/book/{bookId}")
  @Validated
  public List<Review> getReviewsByBook(@PathVariable Long bookId) {
    return reviewService.getReviewsByBook(bookId);
  }

  @Tag(name="Get All Reviews By User", description="Receive all the reviews of one user by userId")
  @GetMapping("/user/{userId}")
  public List<Review> getReviewsByUser(@PathVariable String userId) {
    return reviewService.getReviewsByUser(userId);
  }
}