package com.library.core.book.dto;

import com.library.core.book.domain.Language;
import lombok.Data;

import java.util.List;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String summary;
    private String imprint;
    private String isbn;
    private Language language;
    private List<GenreOrLanguageDto> genres;
    private List<AuthorDto> authors;
}
