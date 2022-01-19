package com.library.core.book.web.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class BookBaseRequest {
    @NotNull
    private String title;
    @NotNull
    private String summary;
    @NotNull
    private String imprint;
    @NotNull
    private String isbn;
    @NotNull
    private Long languageId;
    @NotNull
    private List<Long> genresIds = new ArrayList<>();
    @NotNull
    private List<Long> authorsIds = new ArrayList<>();
}
