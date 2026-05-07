package ch.holiuk.anna.pocket_library.book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;
  private String title;
  private String author;
  private int totalPages;
  private Integer pagesRead = 0;
  private Double progress = 0.0;
  private Integer rating;
}

//TODO: Assign roles to all endpoints
//      Swagger @Tag(name="", description="") annotations on each controller and endpoint
//      UnitTests
//      Documentation
//      Test Endpoints
