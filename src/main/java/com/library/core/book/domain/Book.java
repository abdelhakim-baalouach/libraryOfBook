package com.library.core.book.domain;
import com.library.core.common.AbstractAuditingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@Where(clause = "state_enum <> 'DELETED'")
@SQLDelete(sql = "UPDATE books SET state_enum = 'DELETED' WHERE id=?", check = ResultCheckStyle.COUNT)
@NoArgsConstructor
public class Book extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String summary;
    private String imprint;
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "books_genres",
            joinColumns = {@JoinColumn(name = "books_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "genres_id", nullable = false, updatable = false)}
    )
    private List<Genre> genres;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "books_authors",
            joinColumns = {@JoinColumn(name = "books_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "authors_id", nullable = false, updatable = false)}
    )
    private List<Author> authors;

    public Book(String title, String summary, String imprint, String isbn, Language language) {
        this.title = title;
        this.summary = summary;
        this.imprint = imprint;
        this.isbn = isbn;
        this.language = language;
    }
}
