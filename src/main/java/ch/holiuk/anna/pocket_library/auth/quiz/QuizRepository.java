package ch.holiuk.anna.pocket_library.auth.quiz;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long>{
  List<Quiz> findByFavoriteGenre(String genre);

  List<Quiz> findByLikedLastBookTrueAndFavoriteGenre(String genre);

  Optional<Quiz> findByUserId(Long userId);
}
