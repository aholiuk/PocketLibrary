package ch.holiuk.anna.pocket_library.friend;

import ch.holiuk.anna.pocket_library.user.User;
import ch.holiuk.anna.pocket_library.user.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class FriendService {

  private final FriendRepository friendRepository;
  private final UserRepository userRepository;

  public FriendService(FriendRepository friendRepository,
                       UserRepository userRepository) {
    this.friendRepository = friendRepository;
    this.userRepository = userRepository;
  }

  // search user by username — returns null if not found or is admin
  public User searchByUsername(String username, String currentUserId) {
    Optional<User> found = userRepository.findByUsername(username);

    if (found.isEmpty()) return null;

    User user = found.get();

    // prevent finding yourself
    if (user.getKeycloakId().equals(currentUserId)) return null;

    return user;
  }

  public void addFriend(String userId, String friendId) {

    if (userId.equals(friendId)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot add yourself as a friend");
    }

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    User friendUser = userRepository.findById(friendId)
            .orElseThrow(() -> new RuntimeException("Friend not found"));

    Friend friend = new Friend();
    friend.setUser(user);
    friend.setFriend(friendUser);

    friendRepository.save(friend);
  }

  public List<Friend> getFriends(String userId) {
    return friendRepository.findByUserKeycloakId(userId);
  }

  public void deleteFriend(Long friendshipId, String userId) {
    Friend friend = friendRepository.findById(friendshipId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Friendship not found"));

    // make sure the user owns this friendship
    if (!friend.getUser().getKeycloakId().equals(userId)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your friendship to delete");
    }

    friendRepository.deleteById(friendshipId);
  }
}