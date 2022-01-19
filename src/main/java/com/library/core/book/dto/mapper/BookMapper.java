package com.library.core.book.dto.mapper;

import com.library.core.book.domain.Author;
import com.library.core.book.domain.Book;
import com.library.core.book.domain.Genre;
import com.library.core.book.domain.Language;
import com.library.core.book.dto.AuthorDto;
import com.library.core.book.dto.BookDto;
import com.library.core.book.dto.GenreOrLanguageDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    
    BookDto mapToBookDto(Book book);
    
    AuthorDto mapToAuthorDto(Author author);

    GenreOrLanguageDto mapToGenreDto(Genre genre);


    GenreOrLanguageDto mapToLanguageDto(Language language);


}
