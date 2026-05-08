package ch.holiuk.anna.pocket_library.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @Operation(summary = "Get the currently logged-in user")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Current user returned"),
          @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/me")
  public User getMe(@AuthenticationPrincipal Jwt jwt) {
    return userService.getOrCreateUser(jwt);
  }



  @Operation(summary = "Get all users – admin only")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "User list returned"),
          @ApiResponse(responseCode = "403", description = "Access denied – admin role required")
  })
  @PreAuthorize("hasAuthority('ROLE_admin')")
  @GetMapping
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

}