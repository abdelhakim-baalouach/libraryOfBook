package com.library.core.book_instance.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateBookIntanceBaseRequest {
    @NotNull
    private Long bookId;
}
