package com.library.core.book_instance.dto;

import com.library.core.book_instance.util.StatusBookEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class OccupationDto extends BookInstanceDto {
    private Long id;
    private OffsetDateTime dueBack;
    private StatusBookEnum status;

}
