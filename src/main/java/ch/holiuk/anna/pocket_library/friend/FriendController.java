package ch.holiuk.anna.pocket_library.friend;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {

  private final FriendService friendService;

  public FriendController(FriendService friendService) {
    this.friendService = friendService;
  }

  @PostMapping("/{friendId}")
  public void addFriend(@AuthenticationPrincipal Jwt jwt,
                        @PathVariable String friendId) {

    String userId = jwt.getSubject();

    friendService.addFriend(userId, friendId);
  }

  @GetMapping
  public List<Friend> getFriends(@AuthenticationPrincipal Jwt jwt) {

    String userId = jwt.getSubject();

    return friendService.getFriends(userId);
  }
}