package com.library.core.book_instance.domain;

import com.library.core.book_instance.web.request.SubscriberBaseRequest;
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
@Table(name = "subscribers")
@Getter
@Setter
@Where(clause = "state_enum <> 'DELETED'")
@SQLDelete(sql = "UPDATE subscribers SET state_enum = 'DELETED' WHERE id=?", check = ResultCheckStyle.COUNT)
@NoArgsConstructor
public class Subscriber extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private OffsetDateTime dateOfbirth;

    @Column(unique=true)
    private String idCard;
    private String email;
    private String phone;

    public Subscriber(SubscriberBaseRequest request) {
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.dateOfbirth = request.getDateOfbirth();
        this.idCard = request.getIdCard();
        this.email = request.getEmail();
        this.phone = request.getPhone();
    }
}
