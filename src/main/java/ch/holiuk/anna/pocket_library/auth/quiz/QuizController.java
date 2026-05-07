package ch.holiuk.anna.pocket_library.auth.quiz;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
public class QuizController {

  private final QuizService quizService;

  public QuizController(QuizService quizService) {
    this.quizService = quizService;
  }

  @PostMapping
  public Quiz submitQuiz(@RequestBody Quiz quiz) {
    return quizService.saveResponse(quiz);
  }
}
