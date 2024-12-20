package study.guilhermerodrigues17.study_libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "author", schema = "public")
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, name = "birthdate")
    private LocalDate birthDate;

    @Column(nullable = false, length = 50)
    private String nationality;

    @OneToMany(mappedBy = "author")
    private List<Book> books;
}
