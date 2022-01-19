package com.library.core.book_instance.domain;

import com.library.core.book.domain.Book;
import com.library.core.common.AbstractAuditingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "book_instances")
@Getter
@Setter
@Where(clause = "state_enum <> 'DELETED'")
@SQLDelete(sql = "UPDATE book_instances SET state_enum = 'DELETED' WHERE uid=?", check = ResultCheckStyle.COUNT)
@NoArgsConstructor
public class BookInstance extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uid;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public BookInstance(Book book) {
        this.book = book;
    }
}
