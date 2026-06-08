package ch.holiuk.anna.pocket_library.auth;

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

  public AuthController(KeycloakService keycloakService) {
    this.keycloakService = keycloakService;
  }

  @Tag(name="Register", description="Register user")
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
    try {
      keycloakService.createUser(request);
      return ResponseEntity.ok("User created successfully");
    } catch (HttpClientErrorException.Conflict e) {
      return ResponseEntity.status(409).body("User already exists");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
    }
  }

  @Tag(name="Login", description="Login user")
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