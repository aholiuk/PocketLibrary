package ch.holiuk.anna.pocket_library.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.Map;

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
    user.put("firstName", req.getFirstName());
    user.put("lastName", req.getLastName());
    user.put("enabled", true);

    Map<String, Object> credential = new HashMap<>();
    credential.put("type", "password");
    credential.put("value", req.getPassword());
    credential.put("temporary", false);

    user.put("credentials", List.of(credential));

    HttpEntity<Map<String, Object>> request =
            new HttpEntity<>(user, headers);
    ResponseEntity<String> createResponse = restTemplate.postForEntity(url, request, String.class);

    // 2. get the new user's id from Location header
    String location = createResponse.getHeaders().getFirst("Location");
    String userId = location.substring(location.lastIndexOf("/") + 1);

    // 3. get the 'read' role from the pocket-library client
    String clientId = getClientId(token);
    String roleUrl = serverUrl + "/admin/realms/" + targetRealm + "/clients/" + clientId + "/roles/read";

    HttpEntity<Void> roleRequest = new HttpEntity<>(headers);
    ResponseEntity<Map> roleResponse = restTemplate.exchange(roleUrl, HttpMethod.GET, roleRequest, Map.class);
    Map<String, Object> readRole = roleResponse.getBody();

    // 4. assign the 'read' role to the new user
    String assignUrl = serverUrl + "/admin/realms/" + targetRealm + "/users/" + userId + "/role-mappings/clients/" + clientId;
    HttpEntity<List<Map<String, Object>>> assignRequest = new HttpEntity<>(List.of(readRole), headers);

    restTemplate.postForEntity(assignUrl, assignRequest, String.class);
  }

  private String getClientId(String token) {
    String url = serverUrl + "/admin/realms/" + targetRealm + "/clients?clientId=pocket-library";

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);

    HttpEntity<Void> request = new HttpEntity<>(headers);
    ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, request, List.class);

    Map<String, Object> client = (Map<String, Object>) response.getBody().get(0);
    return (String) client.get("id");
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

  public Map<String, String> login(LoginRequest request) {
    String url = serverUrl + "/realms/" + targetRealm + "/protocol/openid-connect/token";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "password");
    body.add("client_id", "pocket-library"); // your frontend client
    body.add("username", request.getUsername());
    body.add("password", request.getPassword());

    HttpEntity<MultiValueMap<String, String>> httpRequest =
            new HttpEntity<>(body, headers);

    ResponseEntity<Map> response =
            restTemplate.postForEntity(url, httpRequest, Map.class);

    Map<String, String> tokens = new HashMap<>();
    tokens.put("access_token", (String) response.getBody().get("access_token"));
    tokens.put("refresh_token", (String) response.getBody().get("refresh_token"));

    return tokens;
  }
}
