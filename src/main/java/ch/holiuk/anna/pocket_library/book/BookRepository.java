package ch.holiuk.anna.pocket_library.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
  List<Book> findByKeycloakId(String keycloakId);
  void deleteByKeycloakId(String keycloakId);

}
