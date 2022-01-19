package com.library.core.book_instance.domain;

import com.library.core.book_instance.util.StatusBookEnum;
import com.library.core.common.AbstractAuditingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "occupations")
@Getter
@Setter
@Where(clause = "state_enum <> 'DELETED'")
@SQLDelete(sql = "UPDATE occupations SET state_enum = 'DELETED' WHERE id=?", check = ResultCheckStyle.COUNT)
@NoArgsConstructor
public class Occupation  extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime dueBack;

    @Enumerated(EnumType.STRING)
    private StatusBookEnum status;

    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "book_instance_id")
    private BookInstance bookInstance;

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;

    public Occupation(BookInstance bookInstance) {
        this.bookInstance = bookInstance;
        this.status = StatusBookEnum.FREE;
        this.active = true;
    }

    public Occupation(BookInstance instance, Subscriber subscriber, OffsetDateTime dueBack) {
        this.bookInstance = instance;
        this.subscriber = subscriber;
        this.status = StatusBookEnum.Occupied;
        this.active = true;
        this.dueBack = dueBack;
    }
}
