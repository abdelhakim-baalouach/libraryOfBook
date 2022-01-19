package com.library.core.book_instance.dto;

import com.library.core.book.domain.Language;
import com.library.core.book.dto.AuthorDto;
import com.library.core.book.dto.GenreOrLanguageDto;
import lombok.Data;

import java.util.List;
@Data
public class BookInstanceDto  {
    private String uid;
    private Long bookId;
    private String title;
    private String summary;
    private String imprint;
    private String isbn;
    private Language language;
    private List<GenreOrLanguageDto> genres;
    private List<AuthorDto> authors;
}
