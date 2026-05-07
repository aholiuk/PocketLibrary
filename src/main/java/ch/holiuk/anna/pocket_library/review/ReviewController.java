package ch.holiuk.anna.pocket_library.review;

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
  public Review createReview(@RequestParam Long userId,
                             @RequestParam Long bookId,
                             @RequestBody String text) {

    return reviewService.createReview(userId, bookId, text);
  }

  @GetMapping
  public List<Review> getAllReviews() {
    return reviewService.getAllReviews();
  }

  @GetMapping("/book/{bookId}")
  public List<Review> getReviewsByBook(@PathVariable Long bookId) {
    return reviewService.getReviewsByBook(bookId);
  }

  @GetMapping("/user/{userId}")
  public List<Review> getReviewsByUser(@PathVariable Long userId) {
    return reviewService.getReviewsByUser(userId);
  }
}
