package ch.holiuk.anna.pocket_library.recommendation;

import ch.holiuk.anna.pocket_library.quiz.Quiz;
import ch.holiuk.anna.pocket_library.quiz.QuizRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;

@Service
public class RecommendationService {

  private final QuizRepository quizRepository;

  public RecommendationService(QuizRepository quizRepository) {
    this.quizRepository = quizRepository;
  }

  public List<String> recommend(String userId) {
    Optional<Quiz> quizOpt = quizRepository.findByUserId(userId);

    if (quizOpt.isEmpty()) {
      return List.of();
    }

    Quiz userQuiz = quizOpt.get();
    String genre = userQuiz.getFavoriteGenre();

    if (Boolean.TRUE.equals(userQuiz.getLikedLastBook())) {
      return quizRepository
              .findByLikedLastBookTrueAndFavoriteGenre(genre)
              .stream()
              .map(Quiz::getLastBookRead)
              .distinct()
              .toList();
    }

    return quizRepository
            .findByFavoriteGenre(genre)
            .stream()
            .map(Quiz::getLastBookRead)
            .distinct()
            .toList();
  }
}