package com.library.core.book_instance.service;

import com.library.core.book.domain.Book;
import com.library.core.book_instance.domain.BookInstance;
import com.library.core.book_instance.dto.BookInstanceDto;
import com.library.core.book_instance.web.request.BookInstanceBaseRequest;
import com.library.core.book_instance.web.request.UpdateBookIntanceBaseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BookInstanceService {
    BookInstance findBookInstanceOrThrow(String id);

    List<BookInstance> createInstances(int numberOfInstances, Book book);

    BookInstanceDto getBookInstance(String id);

    Page<BookInstanceDto> findAllBookInstance(Specification<BookInstance> specification, Pageable pageable);

    List<BookInstanceDto> create(BookInstanceBaseRequest request);

    BookInstanceDto update(String id, UpdateBookIntanceBaseRequest request);

    void delete(String id);
}
