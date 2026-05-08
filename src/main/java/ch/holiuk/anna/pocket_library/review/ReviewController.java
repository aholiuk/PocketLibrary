package ch.holiuk.anna.pocket_library.review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @Operation(summary = "Create a review for a book")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Review created"),
          @ApiResponse(responseCode = "404", description = "Book or user not found"),
          @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  @PreAuthorize("hasAnyAuthority('ROLE_read', 'ROLE_admin')")
  @PostMapping
  public Review createReview(@AuthenticationPrincipal Jwt jwt,
                             @RequestParam Long bookId,
                             @RequestBody String text) {

    String userId = jwt.getSubject();

    return reviewService.createReview(userId, bookId, text);
  }

  @Operation(summary = "Get all reviews")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "List of reviews returned"),
          @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  @PreAuthorize("hasAnyAuthority('ROLE_read', 'ROLE_admin')")
  @GetMapping
  public List<Review> getAllReviews() {
    return reviewService.getAllReviews();
  }

  @Operation(summary = "Get all reviews for a specific book")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Reviews returned"),
          @ApiResponse(responseCode = "404", description = "Book not found")
  })
  @PreAuthorize("hasAnyAuthority('ROLE_read', 'ROLE_admin')")
  @GetMapping("/book/{bookId}")
  @Validated
  public List<Review> getReviewsByBook(@PathVariable Long bookId) {
    return reviewService.getReviewsByBook(bookId);
  }

  @Operation(summary = "Get all reviews by a specific user")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Reviews returned"),
          @ApiResponse(responseCode = "404", description = "User not found")
  })
  @PreAuthorize("hasAnyAuthority('ROLE_read', 'ROLE_admin')")
  @GetMapping("/user/{userId}")
  public List<Review> getReviewsByUser(@PathVariable String userId) {
    return reviewService.getReviewsByUser(userId);
  }
}