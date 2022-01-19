package com.library.core.book.web.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class GenreOrLanguageBaseRequest {
    @NotNull
    private String name;
}
