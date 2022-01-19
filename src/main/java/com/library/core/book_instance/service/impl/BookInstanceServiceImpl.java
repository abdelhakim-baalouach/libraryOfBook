package com.library.core.book_instance.service.impl;

import com.library.core.book.domain.Book;
import com.library.core.book.service.BookService;
import com.library.core.book_instance.domain.BookInstance;
import com.library.core.book_instance.dto.BookInstanceDto;
import com.library.core.book_instance.dto.mapper.BookInstanceMapper;
import com.library.core.book_instance.repository.BookInstanceRepository;
import com.library.core.book_instance.service.BookInstanceService;
import com.library.core.book_instance.service.OccupationService;
import com.library.core.book_instance.web.request.BookInstanceBaseRequest;
import com.library.core.book_instance.web.request.UpdateBookIntanceBaseRequest;
import com.library.error.EntityNotFoundException;
import com.library.util.MessageUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookInstanceServiceImpl implements BookInstanceService {
    private final BookInstanceRepository bookInstanceRepository;
    private final BookService bookService;
    private final OccupationService occupationService;
    private final MessageUtil messageUtil;
    private final BookInstanceMapper bookInstanceMapper;

    public BookInstanceServiceImpl(
            @Lazy BookInstanceRepository bookInstanceRepository,
            @Lazy BookService bookService,
            @Lazy OccupationService occupationService,
            MessageUtil messageUtil,
            BookInstanceMapper bookInstanceMapper
    ) {
        this.bookInstanceRepository = bookInstanceRepository;
        this.bookService = bookService;
        this.occupationService = occupationService;
        this.messageUtil = messageUtil;
        this.bookInstanceMapper = bookInstanceMapper;
    }

    @Override
    public BookInstance findBookInstanceOrThrow(String id) {
        return this.bookInstanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("bookIntance.NotFound", id)));
    }

    @Override
    public List<BookInstance> createInstances(int numberOfInstances, Book book) {
        var listInstances = new ArrayList<BookInstance>();
        for (int i = 0; i < 5; i++) {
            var bookInstance = this.save(new BookInstance(book));
            listInstances.add(bookInstance);
            this.occupationService.create(bookInstance);
        }
        return listInstances;
    }

    @Override
    public BookInstanceDto getBookInstance(String id) {
        var bookInstance = this.findBookInstanceOrThrow(id);
        return this.mapToBookInstanceDto(bookInstance);
    }

    @Override
    public Page<BookInstanceDto> findAllBookInstance(Specification<BookInstance> specification, Pageable pageable) {
        return this.bookInstanceRepository
                .findAll(specification, pageable)
                .map(this::mapToBookInstanceDto);
    }

    @Override
    public List<BookInstanceDto> create(BookInstanceBaseRequest request) {
        var book = this.bookService.findBookOrThrow(request.getBookId());
        var listInstances = this.createInstances(request.getNumberOfIntance(), book);
        return listInstances
                .stream()
                .map(this::mapToBookInstanceDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookInstanceDto update(String id, UpdateBookIntanceBaseRequest request) {
        var book = this.bookService.findBookOrThrow(request.getBookId());
        var bookInstance = this.findBookInstanceOrThrow(id);
        bookInstance.setBook(book);
        return this.mapToBookInstanceDto(this.save(bookInstance));
    }

    @Override
    public void delete(String id) {
        try {
            this.bookInstanceRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("bookIntance.NotFound", id));
        }
    }

    private BookInstance save(BookInstance bookInstance) {
        return this.bookInstanceRepository.save(bookInstance);
    }

    private BookInstanceDto mapToBookInstanceDto(BookInstance bookInstance) {
        return this.bookInstanceMapper.mapToBookInstanceDto(bookInstance, occupationService);
    }
}
