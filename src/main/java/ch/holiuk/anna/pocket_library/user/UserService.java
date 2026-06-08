package ch.holiuk.anna.pocket_library.user;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import ch.holiuk.anna.pocket_library.auth.KeycloakService;
import ch.holiuk.anna.pocket_library.book.BookRepository;
import ch.holiuk.anna.pocket_library.friend.FriendRepository;
import java.util.List;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final KeycloakService keycloakService;
  private final BookRepository bookRepository;
  private final FriendRepository friendRepository;

  public UserService(UserRepository userRepository,
                     KeycloakService keycloakService,
                     BookRepository bookRepository,
                     FriendRepository friendRepository) {
    this.userRepository = userRepository;
    this.keycloakService = keycloakService;
    this.bookRepository = bookRepository;
    this.friendRepository = friendRepository;
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
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public void deleteUser(String keycloakId) {
    // delete user's books
    bookRepository.deleteByKeycloakId(keycloakId);

    // delete user's friends
    friendRepository.deleteByUserKeycloakId(keycloakId);
    friendRepository.deleteByFriendKeycloakId(keycloakId);

    // delete from our database
    userRepository.deleteById(keycloakId);

    // delete from Keycloak
    keycloakService.deleteUser(keycloakId);
  }
}
