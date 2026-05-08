package ch.holiuk.anna.pocket_library.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private BookService bookService;

  @Test
  void getAllBooks_withReadRole_shouldReturn200() throws Exception {
    when(bookService.getAllBooks()).thenReturn(List.of());

    mockMvc.perform(get("/books")
                    .with(jwt().authorities(
                            new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_read")
                    )))
            .andExpect(status().isOk());
  }

  @Test
  void getAllBooks_withoutAuth_shouldReturn401() throws Exception {
    mockMvc.perform(get("/books"))
            .andExpect(status().isUnauthorized());
  }

  @Test
  void addBook_withAdminRole_shouldReturn200() throws Exception {
    Book book = new Book();
    book.setTitle("Test");
    book.setAuthor("Author");
    when(bookService.addBook(any())).thenReturn(book);

    mockMvc.perform(post("/books")
                    .with(jwt().authorities(
                            new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_admin")
                    ))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"title\":\"Test\",\"author\":\"Author\",\"totalPages\":100}"))
            .andExpect(status().isOk());
  }

  @Test
  void addBook_withReadRole_shouldReturn403() throws Exception {
    mockMvc.perform(post("/books")
                    .with(jwt().authorities(
                            new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_read")
                    ))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"title\":\"Test\",\"author\":\"Author\",\"totalPages\":100}"))
            .andExpect(status().isForbidden());
  }

  @Test
  void deleteBook_withReadRole_shouldReturn403() throws Exception {
    mockMvc.perform(delete("/books/1")
                    .with(jwt().authorities(
                            new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_read")
                    )))
            .andExpect(status().isForbidden());
  }

  @Test
  void deleteBook_withAdminRole_shouldReturn200() throws Exception {
    mockMvc.perform(delete("/books/1")
                    .with(jwt().authorities(
                            new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_admin")
                    )))
            .andExpect(status().isOk());
  }
}
