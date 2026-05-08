package ch.holiuk.anna.pocket_library.review;

import ch.holiuk.anna.pocket_library.book.Book;
import ch.holiuk.anna.pocket_library.book.BookRepository;
import ch.holiuk.anna.pocket_library.user.User;
import ch.holiuk.anna.pocket_library.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;
  private final BookRepository bookRepository;

  public ReviewService(ReviewRepository reviewRepository,
                       UserRepository userRepository,
                       BookRepository bookRepository) {
    this.reviewRepository = reviewRepository;
    this.userRepository = userRepository;
    this.bookRepository = bookRepository;
  }

  public Review createReview(String userId, Long bookId, String text) {

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));

    Review review = new Review();
    review.setText(text);
    review.setUser(user);
    review.setBook(book);

    return reviewRepository.save(review);
  }

  public List<Review> getAllReviews() {
    return reviewRepository.findAll();
  }

  public List<Review> getReviewsByBook(Long bookId) {
    return reviewRepository.findByBookId(bookId);
  }

  public List<Review> getReviewsByUser(String userId) {
    return reviewRepository.findByUserKeycloakId(userId);
  }
}