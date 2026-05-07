package ch.holiuk.anna.pocket_library.friend;

import org.springframework.stereotype.Service;
import ch.holiuk.anna.pocket_library.user.UserRepository;

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

  public void addFriend(Long userId, Long friendId) {

    userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User does not exist"));

    userRepository.findById(friendId)
            .orElseThrow(() -> new RuntimeException("Friend does not exist"));

    Friend friend = new Friend();
    friend.setUserId(userId);
    friend.setFriendId(friendId);

    friendRepository.save(friend);
  }

  public List<Friend> getFriends(Long userId) {
    return friendRepository.findByUserId(userId);
  }
}