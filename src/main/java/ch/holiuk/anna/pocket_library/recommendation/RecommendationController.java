package ch.holiuk.anna.pocket_library.recommendation;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

  private final RecommendationService recommendationService;

  public RecommendationController(RecommendationService recommendationService) {
    this.recommendationService = recommendationService;
  }

  @GetMapping("/{userId}")
  public List<String> get(@PathVariable Long userId) {
    return recommendationService.recommend(userId);
  }
}