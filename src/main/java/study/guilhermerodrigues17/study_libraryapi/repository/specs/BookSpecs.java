package study.guilhermerodrigues17.study_libraryapi.repository.specs;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import study.guilhermerodrigues17.study_libraryapi.model.Book;
import study.guilhermerodrigues17.study_libraryapi.model.BookGenres;

public class BookSpecs {

    public static Specification<Book> isbnEqual(String isbn) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> titleLike(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("title")), "%" + title.toUpperCase() + "%");
    }

    public static Specification<Book> genreEqual(BookGenres genre) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("genre"), genre);
    }

    public static Specification<Book> publicationYearEqual(Integer publicationYear) {
        return (root, query, criteriaBuilder) -> {
            Expression<String> criteria = criteriaBuilder.function(
                    "to_char",
                    String.class,
                    root.get("publicationDate"),
                    criteriaBuilder.literal("YYYY")
            );
            return criteriaBuilder.equal(criteria, publicationYear.toString());
        };
    }

    public static Specification<Book> authorNameLike(String name) {
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> authorJoin = root.join("author", JoinType.INNER);
            return criteriaBuilder.like(
                    criteriaBuilder.upper(authorJoin.get("name")),
                    "%" + name.toUpperCase() + "%"
            );
        };
    }
}
