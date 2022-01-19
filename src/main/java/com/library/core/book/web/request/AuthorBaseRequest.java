package com.library.core.book.web.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Setter
@Getter
public class AuthorBaseRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private OffsetDateTime dateOfbirth;
    private OffsetDateTime dateOfDeath;
}
