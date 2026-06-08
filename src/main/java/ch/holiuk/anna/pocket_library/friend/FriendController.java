package ch.holiuk.anna.pocket_library.friend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import ch.holiuk.anna.pocket_library.user.User;

@RestController
@Tag(name = "Friends", description = "Manage friend connections")
@RequestMapping("/friends")
public class FriendController {

  private final FriendService friendService;

  public FriendController(FriendService friendService) {
    this.friendService = friendService;
  }

  // search user by username before adding
  @Operation(summary = "Search user by username")
  @PreAuthorize("hasAnyAuthority('read', 'admin')")
  @GetMapping("/search")
  public ResponseEntity<?> searchUser(@RequestParam String username,
                                      @AuthenticationPrincipal Jwt jwt) {
    String currentUserId = jwt.getSubject();
    User found = friendService.searchByUsername(username, currentUserId);

    if (found == null) {
      return ResponseEntity.status(404).body("User not found");
    }
    return ResponseEntity.ok(found);
  }

  @Operation(summary = "Add a user as a friend")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Friend added"),
          @ApiResponse(responseCode = "404", description = "User not found"),
          @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  @PreAuthorize("hasAnyAuthority('read', 'admin')")
  @PostMapping("/{friendId}")
  public ResponseEntity<String> addFriend(@AuthenticationPrincipal Jwt jwt,
                                          @PathVariable String friendId) {
    String userId = jwt.getSubject();
    try {
      friendService.addFriend(userId, friendId);
      return ResponseEntity.ok("Friend added");
    } catch (Exception e) {
      return ResponseEntity.status(400).body(e.getMessage());
    }
  }

  @Operation(summary = "Get my friend list")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Friend list returned"),
          @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  @PreAuthorize("hasAnyAuthority('read', 'admin')")
  @GetMapping
  public List<Friend> getFriends(@AuthenticationPrincipal Jwt jwt) {

    String userId = jwt.getSubject();
    return friendService.getFriends(userId);
  }

  // delete a friendship
  @Operation(summary = "Remove a friend")
  @PreAuthorize("hasAnyAuthority('read', 'admin')")
  @DeleteMapping("/{friendshipId}")
  public ResponseEntity<String> deleteFriend(@PathVariable Long friendshipId,
                                             @AuthenticationPrincipal Jwt jwt) {
    String userId = jwt.getSubject();
    try {
      friendService.deleteFriend(friendshipId, userId);
      return ResponseEntity.ok("Friend removed");
    } catch (Exception e) {
      return ResponseEntity.status(400).body(e.getMessage());
    }
  }
}