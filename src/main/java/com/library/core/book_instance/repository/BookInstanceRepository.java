package com.library.core.book_instance.repository;

import com.library.core.book.domain.Book;
import com.library.core.book_instance.domain.BookInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookInstanceRepository extends JpaRepository<BookInstance, String> {
    List<BookInstance> findAllByBook(Book book);
    Page<BookInstance> findAll(Specification<BookInstance> specification, Pageable pageable);
}
