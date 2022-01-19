package com.library.core.book.domain;

import com.library.core.common.AbstractAuditingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "genres")
@Getter
@Setter
@Where(clause = "state_enum <> 'DELETED'")
@SQLDelete(sql = "UPDATE genres SET state_enum = 'DELETED' WHERE id=?", check = ResultCheckStyle.COUNT)
@NoArgsConstructor
public class Genre extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
