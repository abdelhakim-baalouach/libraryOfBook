package com.library.core.book.domain;

import com.library.core.book.web.request.AuthorBaseRequest;
import com.library.core.common.AbstractAuditingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "authors")
@Getter
@Setter
@Where(clause = "state_enum <> 'DELETED'")
@SQLDelete(sql = "UPDATE authors SET state_enum = 'DELETED' WHERE id=?", check = ResultCheckStyle.COUNT)
@NoArgsConstructor
public class Author extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private OffsetDateTime dateOfbirth;
    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private OffsetDateTime dateOfDeath;

    public Author(AuthorBaseRequest request) {
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.dateOfbirth = request.getDateOfbirth();
        this.dateOfDeath = request.getDateOfDeath();
    }
}
