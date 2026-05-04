package ch.holiuk.anna.pocket_library.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(User user) {
    return userRepository.save(user);
  }

  public User login(String username, String password) {

    User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new RuntimeException("User not found");
    }

    return user;
  }

  public User getUserById(Long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
  }
}
