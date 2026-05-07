package ch.holiuk.anna.pocket_library.friend;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Friend {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  private Long friendId;
}
