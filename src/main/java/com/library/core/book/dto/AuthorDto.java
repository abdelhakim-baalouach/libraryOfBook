package com.library.core.book.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class AuthorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private OffsetDateTime dateOfbirth;
    private OffsetDateTime dateOfDeath;
}
