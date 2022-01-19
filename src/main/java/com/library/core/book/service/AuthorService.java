package com.library.core.book.service;

import com.library.core.book.domain.Author;
import com.library.core.book.dto.AuthorDto;
import com.library.core.book.web.request.AuthorBaseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AuthorService {
    List<Author> getAllByIds(List<Long> authorsIds);

    Author findAuthorOrThrow(Long id);

    AuthorDto getAuthor(Long id);

    Page<AuthorDto> findAllAuthors(Specification<Author> specification, Pageable pageable);

    AuthorDto create(AuthorBaseRequest request);

    void delete(Long id);

    AuthorDto update(Long id, AuthorBaseRequest request);
}
