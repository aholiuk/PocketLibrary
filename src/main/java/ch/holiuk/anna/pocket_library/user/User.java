package ch.holiuk.anna.pocket_library.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

  @Id
  private String keycloakId;

  private String username;
}