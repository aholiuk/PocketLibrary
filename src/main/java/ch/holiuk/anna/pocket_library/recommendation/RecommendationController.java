package ch.holiuk.anna.pocket_library.recommendation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="Recommendation", description="Manage recommendations")
@RequestMapping("/recommendations")
public class RecommendationController {

  private final RecommendationService recommendationService;

  public RecommendationController(RecommendationService recommendationService) {
    this.recommendationService = recommendationService;
  }

  @Tag(name="Get Recommendation", description="Receive recommendation based on my answers from quiz")
  @GetMapping("/{userId}")
  public List<String> get(@PathVariable String userId) {
    return recommendationService.recommend(userId);
  }
}