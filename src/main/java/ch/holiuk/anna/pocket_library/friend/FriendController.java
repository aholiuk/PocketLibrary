package ch.holiuk.anna.pocket_library.friend;

import io.swagger.v3.oas.annotations.tags.Tag;
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

  @Tag(name="Add Friend", description="Add new friend from users")
  @PostMapping("/{friendId}")
  public void addFriend(@AuthenticationPrincipal Jwt jwt,
                        @PathVariable String friendId) {

    String userId = jwt.getSubject();

    friendService.addFriend(userId, friendId);
  }

  @Tag(name="Get All Friends", description="Get list of all my friends")
  @GetMapping
  public List<Friend> getFriends(@AuthenticationPrincipal Jwt jwt) {

    String userId = jwt.getSubject();

    return friendService.getFriends(userId);
  }
}