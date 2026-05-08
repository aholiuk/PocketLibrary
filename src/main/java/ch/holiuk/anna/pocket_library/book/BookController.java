package ch.holiuk.anna.pocket_library.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @Operation(summary = "Add a new book to the library")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Book created successfully"),
          @ApiResponse(responseCode = "403", description = "Access denied – admin role required"),
          @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  @PreAuthorize("hasAuthority('ROLE_admin')")
  @PostMapping
  public Book addBook(@RequestBody final Book book) {
    return bookService.addBook(book);
  }


  @Operation(summary = "Get all books from the library")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "List of books returned"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Access denied")
  })
  @PreAuthorize("hasAnyAuthority('ROLE_read', 'ROLE_admin')")
  @GetMapping
  public List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }


  @Operation(summary = "Get a single book by ID")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Book found"),
          @ApiResponse(responseCode = "404", description = "Book not found"),
          @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  @PreAuthorize("hasAnyAuthority('ROLE_read', 'ROLE_admin')")
  @GetMapping("/{id}")
  public Book getBook(@PathVariable Long id) {
    return bookService.getBookById(id);
  }


  @Operation(summary = "Delete a book from the library")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Book deleted"),
          @ApiResponse(responseCode = "403", description = "Access denied – admin role required"),
          @ApiResponse(responseCode = "404", description = "Book not found")
  })
  @PreAuthorize("hasAuthority('ROLE_admin')")
  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
  }


  @Operation(summary = "Update all book information")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Book updated"),
          @ApiResponse(responseCode = "403", description = "Access denied – admin role required"),
          @ApiResponse(responseCode = "404", description = "Book not found")
  })
  @PreAuthorize("hasAuthority('ROLE_admin')")
  @PutMapping("/{id}")
  public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
    return bookService.updateBook(id, updatedBook);
  }


  @Operation(summary = "Update reading progress (pages read)")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Progress updated"),
          @ApiResponse(responseCode = "404", description = "Book not found"),
          @ApiResponse(responseCode = "401", description = "Unauthorized")
  })
  @PreAuthorize("hasAnyAuthority('ROLE_read', 'ROLE_admin')")
  @PatchMapping("/{id}")
  public Book updatePagesRead(
          @PathVariable Long id,
          @RequestBody Integer pagesRead
  ) {
    return bookService.updatePagesRead(id, pagesRead);
  }


  @Operation(summary = "Rate a book (1–10)")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Rating saved"),
          @ApiResponse(responseCode = "400", description = "Invalid rating value"),
          @ApiResponse(responseCode = "404", description = "Book not found")
  })
  @PreAuthorize("hasAnyAuthority('ROLE_read', 'ROLE_admin')")
  @PatchMapping("/{id}/rating")
  public Book rateBook(
          @PathVariable Long id,
          @RequestBody Integer rating
  ) {
    return bookService.rateBook(id, rating);
  }
}