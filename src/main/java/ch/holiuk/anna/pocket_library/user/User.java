package ch.holiuk.anna.pocket_library.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

  @Id
  private String keycloakId;

  private Long id;

  private String username;
  private String password;
}

//for login:
//POST /realms/PocketLibrary/protocol/openid-connect/token