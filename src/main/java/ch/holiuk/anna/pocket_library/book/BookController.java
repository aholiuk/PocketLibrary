package ch.holiuk.anna.pocket_library.book;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="Books", description="Manage books")
@RequestMapping("/books")
public class BookController {
  private final BookService bookService;

  public BookController(final BookService bookService) {
    this.bookService = bookService;
  }

  @Tag(name="Post Book", description="Add new book to the library")
  @PostMapping
  public Book addBook(@RequestBody final Book book) {
    return bookService.addBook(book);
  }

  @Tag(name="Get All Books", description="Get added books from the library")
  @GetMapping
  public List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  @Tag(name="Get Book", description="Get one added book from the library")
  @GetMapping("/{id}")
  public Book getBook(@PathVariable Long id) {
    return bookService.getBookById(id);
  }

  @Tag(name="Delete Book", description="Delete book from the library")
  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
  }

  @Tag(name="Update Book", description="Update information about the book")
  @PutMapping("/{id}")
  public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
    return bookService.updateBook(id, updatedBook);
  }

  @Tag(name="Update Pages", description="Update only the amount of read pages in the book")
  @PatchMapping("/{id}")
  public Book updatePagesRead(
          @PathVariable Long id,
          @RequestBody Integer pagesRead
  ) {
    return bookService.updatePagesRead(id, pagesRead);
  }

  @Tag(name="Rate Book", description="Add only the rating to the book")
  @PatchMapping("/{id}/rating")
  public Book rateBook(
          @PathVariable Long id,
          @RequestBody Integer rating
  ) {
    return bookService.rateBook(id, rating);
  }
}