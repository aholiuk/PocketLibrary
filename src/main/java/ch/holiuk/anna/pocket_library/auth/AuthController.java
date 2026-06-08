package ch.holiuk.anna.pocket_library.auth;

import ch.holiuk.anna.pocket_library.user.User;
import ch.holiuk.anna.pocket_library.user.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import java.util.Map;

@RestController
@Tag(name="Authenticate", description="Keycloak authentication")
@RequestMapping("/auth")
public class AuthController {

  private final KeycloakService keycloakService;
  private final UserRepository userRepository;

  public AuthController(KeycloakService keycloakService, UserRepository userRepository) {
    this.keycloakService = keycloakService;
    this.userRepository = userRepository;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
    try {
      // 1. create user in Keycloak and get their ID back
      String keycloakId = keycloakService.createUser(request);

      // 2. save user to our database
      User user = new User();
      user.setKeycloakId(keycloakId);
      user.setUsername(request.getUsername());
      userRepository.save(user);

      return ResponseEntity.ok("User created successfully");
    } catch (HttpClientErrorException.Conflict e) {
      return ResponseEntity.status(409).body("User already exists");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
      Map<String, String> tokens = keycloakService.login(request);
      return ResponseEntity.ok(tokens);
    } catch (Exception e) {
      return ResponseEntity.status(401).body("Invalid credentials");
    }
  }
}