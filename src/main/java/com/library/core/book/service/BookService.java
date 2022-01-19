package com.library.core.book.service;

import com.library.core.book.domain.Book;
import com.library.core.book.dto.BookDto;
import com.library.core.book.web.request.CreateBookBaseRequest;
import com.library.core.book.web.request.UpdateBookBaseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface BookService {
    BookDto getBook(Long id);
    Book findBookOrThrow(Long id);
    Page<BookDto> findAllBooks(Specification<Book> specification, Pageable pageable);

    BookDto create(CreateBookBaseRequest request);

    void delete(Long id);

    BookDto update(Long id, UpdateBookBaseRequest request);
}
