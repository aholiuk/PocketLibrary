package ch.holiuk.anna.pocket_library.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

  @Autowired
  private BookRepository bookRepository;

  @BeforeEach
  void cleanUp() {
    bookRepository.deleteAll();
  }

  // CREATE
  @Test
  void save_shouldPersistBook() {
    Book book = new Book();
    book.setTitle("1984");
    book.setAuthor("George Orwell");
    book.setTotalPages(328);

    Book saved = bookRepository.save(book);

    assertThat(saved.getId()).isNotNull();
    assertThat(saved.getTitle()).isEqualTo("1984");
    assertThat(saved.getAuthor()).isEqualTo("George Orwell");
  }

  // READ - single
  @Test
  void findById_shouldReturnBook_whenExists() {
    Book book = new Book();
    book.setTitle("Brave New World");
    book.setAuthor("Aldous Huxley");
    book.setTotalPages(311);
    bookRepository.save(book);

    Optional<Book> found = bookRepository.findById(book.getId());

    assertThat(found).isPresent();
    assertThat(found.get().getTitle()).isEqualTo("Brave New World");
  }

  @Test
  void findById_shouldReturnEmpty_whenNotExists() {
    Optional<Book> found = bookRepository.findById(999L);

    assertThat(found).isEmpty();
  }

  // READ - all
  @Test
  void findAll_shouldReturnAllBooks() {
    Book book1 = new Book();
    book1.setTitle("Book A");
    book1.setAuthor("Author A");
    book1.setTotalPages(100);

    Book book2 = new Book();
    book2.setTitle("Book B");
    book2.setAuthor("Author B");
    book2.setTotalPages(200);

    bookRepository.save(book1);
    bookRepository.save(book2);

    List<Book> all = bookRepository.findAll();

    assertThat(all).hasSize(2);
  }

  // UPDATE
  @Test
  void save_shouldUpdateExistingBook() {
    Book book = new Book();
    book.setTitle("Original Title");
    book.setAuthor("Author");
    book.setTotalPages(100);
    bookRepository.save(book);

    book.setTitle("Updated Title");
    book.setPagesRead(50);
    bookRepository.save(book);

    Book updated = bookRepository.findById(book.getId()).orElseThrow();
    assertThat(updated.getTitle()).isEqualTo("Updated Title");
    assertThat(updated.getPagesRead()).isEqualTo(50);
  }

  // DELETE
  @Test
  void delete_shouldRemoveBook() {
    Book book = new Book();
    book.setTitle("To Delete");
    book.setAuthor("Someone");
    book.setTotalPages(100);
    bookRepository.save(book);

    bookRepository.deleteById(book.getId());

    assertThat(bookRepository.findById(book.getId())).isEmpty();
  }

  @Test
  void deleteAll_shouldClearRepository() {
    Book book1 = new Book();
    book1.setTitle("Book A");
    book1.setAuthor("Author A");
    book1.setTotalPages(100);

    bookRepository.save(book1);
    bookRepository.deleteAll();

    assertThat(bookRepository.findAll()).isEmpty();
  }
}