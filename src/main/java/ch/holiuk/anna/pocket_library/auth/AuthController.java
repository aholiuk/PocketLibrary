package ch.holiuk.anna.pocket_library.auth;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final KeycloakService keycloakService;

  public AuthController(KeycloakService keycloakService) {
    this.keycloakService = keycloakService;
  }

  @PostMapping("/register")
  public void register(@RequestBody RegisterRequest request) {
    keycloakService.createUser(request);
  }
}