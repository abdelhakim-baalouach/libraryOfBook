package com.library.core.book.web;

import com.library.core.book.dto.AuthorDto;
import com.library.core.book.service.AuthorService;
import com.library.core.book.util.NotDeletedAuthorSpecification;
import com.library.core.book.web.request.AuthorBaseRequest;
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
@RequestMapping("/author")
public class AuthorRestController {
    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public AuthorDto getOne(@PathVariable Long id) {
        return this.authorService.getAuthor(id);
    }

    @GetMapping
    @ResponseBody
    public Page<AuthorDto> getAll(
            @Or({
                    @Spec(path = "firstName", params = "q", spec = LikeIgnoreCase.class),
                    @Spec(path = "lastName", params = "q", spec = LikeIgnoreCase.class),
            }) NotDeletedAuthorSpecification specification, @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return this.authorService.findAllAuthors(specification, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AuthorDto create(@RequestBody @Valid AuthorBaseRequest request) {
        return this.authorService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        this.authorService.delete(id);
    }

    @PutMapping("/{id}")
    public AuthorDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid AuthorBaseRequest request) {
        return this.authorService.update(id, request);
    }
}
