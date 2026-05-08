package ch.holiuk.anna.pocket_library.quiz;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Quiz", description = "Submit quiz answers for recommendations")
@RequestMapping("/quiz")
public class QuizController {

  private final QuizService quizService;

  public QuizController(QuizService quizService) {
    this.quizService = quizService;
  }

  @Operation(summary = "Submit quiz answers")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Quiz saved"),
          @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  @PreAuthorize("hasAnyAuthority('ROLE_read', 'ROLE_admin')")
  @PostMapping
  public Quiz submitQuiz(@RequestBody Quiz quiz) {
    return quizService.saveResponse(quiz);
  }
}