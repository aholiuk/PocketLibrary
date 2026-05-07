package ch.holiuk.anna.pocket_library.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

  List<Friend> findByUserId(Long userId);
}