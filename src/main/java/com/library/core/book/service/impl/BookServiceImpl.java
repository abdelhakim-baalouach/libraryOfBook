package com.library.core.book.service.impl;

import com.library.core.book.domain.Author;
import com.library.core.book.domain.Book;
import com.library.core.book.domain.Genre;
import com.library.core.book.domain.Language;
import com.library.core.book.dto.BookDto;
import com.library.core.book.dto.mapper.BookMapper;
import com.library.core.book.repository.BookRepository;
import com.library.core.book.service.AuthorService;
import com.library.core.book.service.BookService;
import com.library.core.book.service.GenreService;
import com.library.core.book.service.LanguageService;
import com.library.core.book.web.request.CreateBookBaseRequest;
import com.library.core.book.web.request.UpdateBookBaseRequest;
import com.library.core.book_instance.service.BookInstanceService;
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

@Service
@AllArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final MessageUtil messageUtil;
    private final BookMapper bookMapper;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final LanguageService languageService;
    private final BookInstanceService bookInstanceService;

    @Override
    public BookDto getBook(Long id) {
        var book = this.findBookOrThrow(id);
        return this.mapToBookDto(book);
    }

    private BookDto mapToBookDto(Book book) {
        return this.bookMapper.mapToBookDto(book);
    }

    @Override
    public Book findBookOrThrow(Long id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("book.NotFound", id)));
    }

    @Override
    public Page<BookDto> findAllBooks(Specification<Book> specification, Pageable pageable) {
        return this.bookRepository
                .findAll(specification, pageable)
                .map(this::mapToBookDto);
    }

    @Override
    public BookDto create(CreateBookBaseRequest request) {
        var genres = this.genreService.getAllByIds(request.getGenresIds());
        var authors = this.authorService.getAllByIds(request.getAuthorsIds());
        var language = this.languageService.findLanguageOrThrow(request.getLanguageId());
        var book = new Book(request.getTitle(), request.getSummary(), request.getImprint(), request.getIsbn(), language);
        var bookSaved = this.bookRepository.saveAndFlush(book);
        bookSaved.setAuthors(authors);
        bookSaved.setGenres(genres);
        this.bookInstanceService.createInstances(request.getNumberOfInstances(), bookSaved);
        return this.mapToBookDto(bookSaved);
    }

    @Override
    public void delete(Long id) {
        try {
            this.bookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("book.NotFound", id));
        }
    }

    @Override
    public BookDto update(Long id, UpdateBookBaseRequest request) {
        var book = this.findBookOrThrow(id);
        var genres = this.genreService.getAllByIds(request.getGenresIds());
        var authors = this.authorService.getAllByIds(request.getAuthorsIds());
        var language = this.languageService.findLanguageOrThrow(request.getLanguageId());
        var prepare = this.of(book, request.getTitle(), request.getSummary(), request.getImprint(), request.getIsbn(), language, genres, authors);
        return this.mapToBookDto(prepare);
    }

    private Book of(Book book, String title, String summary, String imprint, String isbn, Language language, List<Genre> genres, List<Author> authors) {
        book.setTitle(title);
        book.setSummary(summary);
        book.setImprint(imprint);
        book.setIsbn(isbn);
        book.setLanguage(language);
        book.setGenres(genres);
        book.setAuthors(authors);
        return book;
    }
}
