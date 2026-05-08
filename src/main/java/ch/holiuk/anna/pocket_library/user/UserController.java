package ch.holiuk.anna.pocket_library.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Users", description = "Manage users")
@RequestMapping("/users")
public class UserController {
  private final UserService userService;
  private final UserRepository userRepository;

  public UserController(UserService userService,
                        UserRepository userRepository) {

    this.userService = userService;
    this.userRepository = userRepository;
  }

  @Tag(name = "Get Current User", description = "Return current user")
  @GetMapping("/me")
  public User getMe(@AuthenticationPrincipal Jwt jwt) {
    return userService.getOrCreateUser(jwt);
  }

  @Tag(name = "Get All User", description = "Return all users")
  @GetMapping
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

}