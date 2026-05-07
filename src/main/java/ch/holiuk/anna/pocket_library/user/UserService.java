package ch.holiuk.anna.pocket_library.user;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getOrCreateUser(Jwt jwt) {

    String keycloakId = jwt.getSubject();
    String username = jwt.getClaim("preferred_username");

    return userRepository.findById(keycloakId)
            .orElseGet(() -> {
              User user = new User();
              user.setKeycloakId(keycloakId);
              user.setUsername(username);
              return userRepository.save(user);
            });
  }
}
