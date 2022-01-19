package com.library.core.book.web;

import com.library.core.book.dto.BookDto;
import com.library.core.book.service.BookService;
import com.library.core.book.util.NotDeletedBookSpecification;
import com.library.core.book.web.request.CreateBookBaseRequest;
import com.library.core.book.web.request.UpdateBookBaseRequest;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public BookDto getOne(@PathVariable Long id) {
        return this.bookService.getBook(id);
    }

    @GetMapping
    @ResponseBody
    public Page<BookDto> getAll(
            @Or({
                    @Spec(path = "title", params = "q", spec = LikeIgnoreCase.class),
                    @Spec(path = "isbn", params = "q", spec = LikeIgnoreCase.class),
            }) NotDeletedBookSpecification specification, @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return this.bookService.findAllBooks(specification, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BookDto create(@RequestBody @Valid CreateBookBaseRequest request) {
        return this.bookService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        this.bookService.delete(id);
    }

    @PutMapping("/{id}")
    public BookDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid UpdateBookBaseRequest request) {
        return this.bookService.update(id, request);
    }
}
