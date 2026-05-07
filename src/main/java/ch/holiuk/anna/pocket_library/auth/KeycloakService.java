package ch.holiuk.anna.pocket_library.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class KeycloakService {

  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${keycloak.server-url}")
  private String serverUrl;

  @Value("${keycloak.realm}")
  private String realm;

  @Value("${keycloak.client-id}")
  private String clientId;

  @Value("${keycloak.client-secret}")
  private String clientSecret;

  @Value("${keycloak.target-realm}")
  private String targetRealm;

  public void createUser(RegisterRequest req) {

    String token = getToken();

    String url = serverUrl + "/admin/realms/" + targetRealm + "/users";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(token);

    Map<String, Object> user = new HashMap<>();
    user.put("username", req.getUsername());
    user.put("email", req.getEmail());
    user.put("enabled", true);

    Map<String, Object> credential = new HashMap<>();
    credential.put("type", "password");
    credential.put("value", req.getPassword());
    credential.put("temporary", false);

    user.put("credentials", List.of(credential));

    HttpEntity<Map<String, Object>> request =
            new HttpEntity<>(user, headers);

    restTemplate.postForEntity(url, request, String.class);
  }

  private String getToken() {

    String url = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "client_credentials");
    body.add("client_id", clientId);
    body.add("client_secret", clientSecret);

    HttpEntity<MultiValueMap<String, String>> request =
            new HttpEntity<>(body, headers);

    ResponseEntity<Map> response =
            restTemplate.postForEntity(url, request, Map.class);

    return (String) response.getBody().get("access_token");
  }
}
