package ch.holiuk.anna.pocket_library.friend;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {

  private final FriendService friendService;

  public FriendController(FriendService friendService) {
    this.friendService = friendService;
  }

  @PostMapping("/{userId}/{friendId}")
  public void addFriend(@PathVariable Long userId,
                        @PathVariable Long friendId) {
    friendService.addFriend(userId, friendId);
  }

  @GetMapping("/{userId}")
  public List<Friend> getFriends(@PathVariable Long userId) {
    return friendService.getFriends(userId);
  }
}

//TODO: Assign roles to all the endpoints