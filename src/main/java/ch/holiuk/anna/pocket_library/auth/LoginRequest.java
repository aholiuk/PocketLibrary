package ch.holiuk.anna.pocket_library.auth;

import lombok.Data;
@Data
public class LoginRequest {
  private String username;
  private String password;
}
