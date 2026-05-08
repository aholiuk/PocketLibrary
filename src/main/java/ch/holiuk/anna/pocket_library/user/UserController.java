package ch.holiuk.anna.pocket_library.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Users", description = "Manage Users")
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Tag(name = "Get User", description = "Get user by ID")
  @GetMapping("/me")
  public User getMe(@AuthenticationPrincipal Jwt jwt) {
    return userService.getOrCreateUser(jwt);
  }

}

//TODO: Assign roles to all the endpoints