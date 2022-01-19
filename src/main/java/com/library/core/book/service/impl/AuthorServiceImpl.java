package com.library.core.book.service.impl;

import com.library.core.book.domain.Author;
import com.library.core.book.dto.AuthorDto;
import com.library.core.book.dto.mapper.BookMapper;
import com.library.core.book.repository.AuthorRepository;
import com.library.core.book.service.AuthorService;
import com.library.core.book.web.request.AuthorBaseRequest;
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
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;
    private final MessageUtil messageUtil;
    private final BookMapper bookMapper;

    @Override
    public List<Author> getAllByIds(List<Long> authorsIds) {
        return authorsIds
                .stream()
                .map(this::findAuthorOrThrow)
                .collect(Collectors.toList());
    }

    @Override
    public Author findAuthorOrThrow(Long id) {
        return this.authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("author.NotFound", id)));
    }

    @Override
    public AuthorDto getAuthor(Long id) {
        var author = this.findAuthorOrThrow(id);
        return this.mapToAuthorDto(author);

    }

    @Override
    public Page<AuthorDto> findAllAuthors(Specification<Author> specification, Pageable pageable) {
        return this.authorRepository
                .findAll(specification, pageable)
                .map(this::mapToAuthorDto);
    }

    @Override
    public AuthorDto create(AuthorBaseRequest request) {
        var author = new Author(request);
        return this.mapToAuthorDto(this.authorRepository.saveAndFlush(author));
    }

    @Override
    public void delete(Long id) {
        try {
            this.authorRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("author.NotFound", id));
        }
    }

    @Override
    public AuthorDto update(Long id, AuthorBaseRequest request) {
        var author = this.findAuthorOrThrow(id);
        var prepare = this.of(author, request);
        return this.mapToAuthorDto(this.authorRepository.saveAndFlush(prepare));
    }

    private Author of(Author author, AuthorBaseRequest request) {
        author.setLastName(request.getLastName());
        author.setFirstName(request.getFirstName());
        author.setDateOfbirth(request.getDateOfbirth());
        author.setDateOfDeath(request.getDateOfDeath());
        return author;
    }

    private AuthorDto mapToAuthorDto(Author author) {
        return this.bookMapper.mapToAuthorDto(author);
    }
}
