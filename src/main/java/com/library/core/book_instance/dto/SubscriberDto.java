package com.library.core.book_instance.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class SubscriberDto {
    private Long id;
    private String fullName;
    private OffsetDateTime dateOfbirth;
    private String idCard;
    private String email;
    private String phone;
    private List<OccupationDto> occupations;
}
