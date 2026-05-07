package ch.holiuk.anna.pocket_library.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  public User getMe(@AuthenticationPrincipal Jwt jwt) {
    return userService.getOrCreateUser(jwt);
  }
}
