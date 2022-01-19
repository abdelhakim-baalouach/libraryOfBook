package com.library.core.book.service;

import com.library.core.book.domain.Genre;
import com.library.core.book.dto.GenreOrLanguageDto;
import com.library.core.book.web.request.GenreOrLanguageBaseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface GenreService {
    List<Genre> getAllByIds(List<Long> genreIds);

    Genre findGenreOrThrow(Long aLong);

    GenreOrLanguageDto getGenre(Long id);

    Page<GenreOrLanguageDto> findAllGenres(Specification<Genre> specification, Pageable pageable);

    GenreOrLanguageDto create(GenreOrLanguageBaseRequest request);

    void delete(Long id);

    GenreOrLanguageDto update(Long id, GenreOrLanguageBaseRequest request);
}
