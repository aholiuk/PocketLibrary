package ch.holiuk.anna.pocket_library.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public Book addBook(Book book) {
    return bookRepository.save(book);
  }

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public Book getBookById(Long id) {
    return bookRepository.findById(id)
            .orElse(null);
  }

  public void deleteBook(Long id) {
    bookRepository.deleteById(id);
  }

  public Book updateBook(Long id, Book updatedBook) {

    Book existingBook = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));

    existingBook.setTitle(updatedBook.getTitle());
    existingBook.setAuthor(updatedBook.getAuthor());
    existingBook.setTotalPages(updatedBook.getTotalPages());

    return bookRepository.save(existingBook);
  }

  public Book updatePagesRead(Long id, int pagesRead) {

    Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));

    book.setPagesRead(pagesRead);

    if (book.getTotalPages() > 0) {
      double progress = (pagesRead * 100.0) / book.getTotalPages();
      book.setProgress(progress);
    }

    return bookRepository.save(book);
  }

  public Book rateBook(Long id, Integer rating) {

    if (rating < 1 || rating > 10) {
      throw new RuntimeException("Rating must be between 1 and 10");
    }

    Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));

    book.setRating(rating);

    return bookRepository.save(book);
  }
}
