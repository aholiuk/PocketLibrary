package ch.holiuk.anna.pocket_library.quiz;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="Quiz", description="Manage quiz")
@RequestMapping("/quiz")
public class QuizController {

  private final QuizService quizService;

  public QuizController(QuizService quizService) {
    this.quizService = quizService;
  }

  @Tag(name="Post Quiz", description="Add quiz answers to the storage")
  @PostMapping
  public Quiz submitQuiz(@RequestBody Quiz quiz) {
    return quizService.saveResponse(quiz);
  }
}