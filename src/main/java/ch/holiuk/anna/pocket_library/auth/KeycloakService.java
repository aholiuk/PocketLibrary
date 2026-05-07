package ch.holiuk.anna.pocket_library.auth;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class KeycloakService {

  private final RestTemplate restTemplate = new RestTemplate();

  private final String serverUrl = "http://localhost:8080";
  private final String realm = "PocketLibrary";

  public void createUser(RegisterRequest request) {

    String adminToken = getAdminToken();

    String url = serverUrl + "/admin/realms/" + realm + "/users";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(adminToken);

    Map<String, Object> user = new HashMap<>();
    user.put("username", request.getUsername());
    user.put("email", request.getEmail());
    user.put("enabled", true);

    Map<String, Object> credentials = new HashMap<>();
    credentials.put("type", "password");
    credentials.put("value", request.getPassword());
    credentials.put("temporary", false);

    user.put("credentials", List.of(credentials));

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(user, headers);

    ResponseEntity<String> response =
            restTemplate.postForEntity(url, entity, String.class);

    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new RuntimeException("Keycloak user creation failed: " + response.getBody());
    }
  }

  private String getAdminToken() {

    String url = serverUrl + "/realms/master/protocol/openid-connect/token";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    String body =
            "client_id=admin-cli" +
                    "&username=admin" +
                    "&password=admin" +
                    "&grant_type=password";

    HttpEntity<String> entity = new HttpEntity<>(body, headers);

    ResponseEntity<Map> response =
            restTemplate.postForEntity(url, entity, Map.class);

    if (response.getBody() == null || response.getBody().get("access_token") == null) {
      throw new RuntimeException("Cannot get admin token from Keycloak");
    }

    return response.getBody().get("access_token").toString();
  }
}
