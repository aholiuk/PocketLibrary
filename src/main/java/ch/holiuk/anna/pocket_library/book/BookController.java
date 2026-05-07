package ch.holiuk.anna.pocket_library.book;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
  private final BookService bookService;

  public BookController(final BookService bookService) {
    this.bookService = bookService;
  }

  @PostMapping
  public Book addBook(@RequestBody final Book book) {
    return bookService.addBook(book);
  }

  @GetMapping
  public List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  @GetMapping("/{id}")
  public Book getBook(@PathVariable Long id) {
    return bookService.getBookById(id);
  }

  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
  }

  @PutMapping("/{id}")
  public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
    return bookService.updateBook(id, updatedBook);
  }

  @PatchMapping("/{id}")
  public Book updatePagesRead(
          @PathVariable Long id,
          @RequestBody Integer pagesRead
  ) {
    return bookService.updatePagesRead(id, pagesRead);
  }

  @PatchMapping("/{id}/rating")
  public Book rateBook(
          @PathVariable Long id,
          @RequestBody Integer rating
  ) {
    return bookService.rateBook(id, rating);
  }
}

//TODO: Assign roles to all the endpoints