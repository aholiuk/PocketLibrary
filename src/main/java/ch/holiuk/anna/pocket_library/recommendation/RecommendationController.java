package ch.holiuk.anna.pocket_library.recommendation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Recommendations", description = "Get book recommendations based on quiz")
@RequestMapping("/recommendations")
public class RecommendationController {

  private final RecommendationService recommendationService;

  public RecommendationController(RecommendationService recommendationService) {
    this.recommendationService = recommendationService;
  }

  @Operation(summary = "Get book recommendations for a user")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Recommendations returned"),
          @ApiResponse(responseCode = "404", description = "Quiz not found for user"),
          @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  @PreAuthorize("hasAnyAuthority('ROLE_read', 'ROLE_admin')")
  @GetMapping("/{userId}")
  public List<String> get(@PathVariable String userId) {
    return recommendationService.recommend(userId);
  }
}