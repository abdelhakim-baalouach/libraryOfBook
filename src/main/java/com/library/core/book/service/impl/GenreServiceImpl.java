package com.library.core.book.service.impl;

import com.library.core.book.domain.Author;
import com.library.core.book.domain.Genre;
import com.library.core.book.dto.GenreOrLanguageDto;
import com.library.core.book.dto.mapper.BookMapper;
import com.library.core.book.repository.GenreRepository;
import com.library.core.book.service.GenreService;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final MessageUtil messageUtil;
    private final BookMapper bookMapper;

    @Override
    public List<Genre> getAllByIds(List<Long> genreIds) {
        return genreIds
                .stream()
                .map(this::findGenreOrThrow)
                .collect(Collectors.toList());
    }

    @Override
    public Genre findGenreOrThrow(Long id) {
        return this.genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("genre.NotFound", id)));
    }

    @Override
    public GenreOrLanguageDto getGenre(Long id) {
        var genre = this.findGenreOrThrow(id);
        return this.mapToGenreDto(genre);
    }

    @Override
    public Page<GenreOrLanguageDto> findAllGenres(Specification<Genre> specification, Pageable pageable) {
        return this.genreRepository
                .findAll(specification, pageable)
                .map(this::mapToGenreDto);
    }

    @Override
    public GenreOrLanguageDto create(GenreOrLanguageBaseRequest request) {
        var genre = new Genre(request.getName());
        return this.mapToGenreDto(this.genreRepository.saveAndFlush(genre));
    }

    @Override
    public void delete(Long id) {
        try {
            this.genreRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("genre.NotFound", id));
        }
    }

    @Override
    public GenreOrLanguageDto update(Long id, GenreOrLanguageBaseRequest request) {
        var genre = this.findGenreOrThrow(id);
        genre.setName(request.getName());
        return this.mapToGenreDto(this.genreRepository.saveAndFlush(genre));
    }

    private GenreOrLanguageDto mapToGenreDto(Genre genre) {
        return this.bookMapper.mapToGenreDto(genre);
    }
}
