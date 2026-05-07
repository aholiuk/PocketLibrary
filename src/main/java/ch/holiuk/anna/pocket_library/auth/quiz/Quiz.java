package ch.holiuk.anna.pocket_library.auth.quiz;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Quiz {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  private String lastBookRead;
  private Boolean likedLastBook;
  private String favoriteGenre;
}
