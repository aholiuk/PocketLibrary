package ch.holiuk.anna.pocket_library.review;

import jakarta.persistence.*;
import lombok.Data;
import ch.holiuk.anna.pocket_library.book.Book;
import ch.holiuk.anna.pocket_library.user.User;

@Data
@Entity
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String text;

  @ManyToOne
  private User user;

  @ManyToOne
  private Book book;
}
