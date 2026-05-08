package ch.holiuk.anna.pocket_library.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    keycloakService.createUser(request);
    return ResponseEntity.ok("User created successfully");
  }
}