package com.library.core.book_instance.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Data
public class SubscriberBaseRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private OffsetDateTime dateOfbirth;
    @NotNull
    private String idCard;
    @NotNull
    private String email;
    @NotNull
    private String phone;
}
