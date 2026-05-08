package ch.holiuk.anna.pocket_library.friend;

import ch.holiuk.anna.pocket_library.user.User;
import ch.holiuk.anna.pocket_library.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

  private final FriendRepository friendRepository;
  private final UserRepository userRepository;

  public FriendService(FriendRepository friendRepository,
                       UserRepository userRepository) {
    this.friendRepository = friendRepository;
    this.userRepository = userRepository;
  }

  public void addFriend(String userId, String friendId) {

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
}