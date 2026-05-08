package ch.holiuk.anna.pocket_library.friend;

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
@Tag(name = "Friends", description = "Manage friend connections")
@RequestMapping("/friends")
public class FriendController {

  private final FriendService friendService;

  public FriendController(FriendService friendService) {
    this.friendService = friendService;
  }

  @Operation(summary = "Add a user as a friend")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Friend added"),
          @ApiResponse(responseCode = "404", description = "User not found"),
          @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  @PreAuthorize("hasAnyAuthority('ROLE_read', 'ROLE_admin')")
  @PostMapping("/{friendId}")
  public void addFriend(@AuthenticationPrincipal Jwt jwt,
                        @PathVariable String friendId) {

    String userId = jwt.getSubject();

    friendService.addFriend(userId, friendId);
  }

  @Operation(summary = "Get my friend list")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Friend list returned"),
          @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  @PreAuthorize("hasAnyAuthority('ROLE_read', 'ROLE_admin')")
  @GetMapping
  public List<Friend> getFriends(@AuthenticationPrincipal Jwt jwt) {

    String userId = jwt.getSubject();

    return friendService.getFriends(userId);
  }
}