package ch.holiuk.anna.pocket_library.quiz;

import org.springframework.stereotype.Service;

@Service
public class QuizService {

  private final QuizRepository quizRepository;

  public QuizService(QuizRepository quizRepository) {
    this.quizRepository = quizRepository;
  }

  public Quiz saveResponse(Quiz quiz) {
    return quizRepository.save(quiz);
  }
}