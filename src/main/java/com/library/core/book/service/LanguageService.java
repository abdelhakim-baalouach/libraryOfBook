package com.library.core.book.service;

import com.library.core.book.domain.Language;
import com.library.core.book.dto.GenreOrLanguageDto;
import com.library.core.book.web.request.GenreOrLanguageBaseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface LanguageService {
    Language findLanguageOrThrow(Long id);

    GenreOrLanguageDto getLanguage(Long id);

    Page<GenreOrLanguageDto> findAllLanguage(Specification<Language> specification, Pageable pageable);

    GenreOrLanguageDto create(GenreOrLanguageBaseRequest request);

    void delete(Long id);

    GenreOrLanguageDto update(Long id, GenreOrLanguageBaseRequest request);
}
