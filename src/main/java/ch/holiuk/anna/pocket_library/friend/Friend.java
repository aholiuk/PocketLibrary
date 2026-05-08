package ch.holiuk.anna.pocket_library.friend;

import ch.holiuk.anna.pocket_library.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Friend {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private User user;

  @ManyToOne
  private User friend;
}
