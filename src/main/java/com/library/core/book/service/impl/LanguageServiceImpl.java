package com.library.core.book.service.impl;

import com.library.core.book.domain.Language;
import com.library.core.book.dto.GenreOrLanguageDto;
import com.library.core.book.dto.mapper.BookMapper;
import com.library.core.book.repository.LanguageRepository;
import com.library.core.book.service.LanguageService;
import com.library.core.book.web.request.GenreOrLanguageBaseRequest;
import com.library.error.EntityNotFoundException;
import com.library.util.MessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository languageRepository;
    private final MessageUtil messageUtil;
    private final BookMapper bookMapper;

    @Override
    public Language findLanguageOrThrow(Long id) {
        return this.languageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("language.NotFound", id)));
    }

    @Override
    public GenreOrLanguageDto getLanguage(Long id) {
        var language = this.findLanguageOrThrow(id);
        return this.mapToLanguageDto(language);
    }

    @Override
    public Page<GenreOrLanguageDto> findAllLanguage(Specification<Language> specification, Pageable pageable) {
        return this.languageRepository
                .findAll(specification, pageable)
                .map(this::mapToLanguageDto);
    }

    @Override
    public GenreOrLanguageDto create(GenreOrLanguageBaseRequest request) {
        var language = new Language(request.getName());
        return this.mapToLanguageDto(this.languageRepository.saveAndFlush(language));
    }

    @Override
    public void delete(Long id) {
        try {
            this.languageRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("language.NotFound", id));
        }
    }

    @Override
    public GenreOrLanguageDto update(Long id, GenreOrLanguageBaseRequest request) {
        var language = this.findLanguageOrThrow(id);
        language.setName(request.getName());
        return this.mapToLanguageDto(this.languageRepository.saveAndFlush(language));
    }

    private GenreOrLanguageDto mapToLanguageDto(Language language) {
        return this.bookMapper.mapToLanguageDto(language);
    }
}
