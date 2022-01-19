package com.library.core.book_instance.web;

import com.library.core.book_instance.dto.BookInstanceDto;
import com.library.core.book_instance.service.BookInstanceService;
import com.library.core.book_instance.util.NotDeletedBookIntanceSpecification;
import com.library.core.book_instance.web.request.BookInstanceBaseRequest;
import com.library.core.book_instance.web.request.UpdateBookIntanceBaseRequest;
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
import java.util.List;

@RestController
@RequestMapping("/bookinstance")
public class BookInstanceRestController {

    private final BookInstanceService bookInstanceService;

    public BookInstanceRestController(BookInstanceService bookInstanceService) {
        this.bookInstanceService = bookInstanceService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public BookInstanceDto getOne(@PathVariable String id) {
        return this.bookInstanceService.getBookInstance(id);
    }

    @GetMapping
    @ResponseBody
    public Page<BookInstanceDto> getAll(
            @Or({
                    @Spec(path = "book.title", params = "q", spec = LikeIgnoreCase.class),
                    @Spec(path = "book.isbn", params = "q", spec = LikeIgnoreCase.class),
            }) NotDeletedBookIntanceSpecification specification, @PageableDefault(sort = "uid", direction = Sort.Direction.ASC) Pageable pageable) {
        return this.bookInstanceService.findAllBookInstance(specification, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<BookInstanceDto> create(@RequestBody @Valid BookInstanceBaseRequest request) {
        return this.bookInstanceService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id){
        this.bookInstanceService.delete(id);
    }

    @PutMapping("/{id}")
    public BookInstanceDto update(@PathVariable(name = "id") String id, @RequestBody @Valid UpdateBookIntanceBaseRequest request) {
        return this.bookInstanceService.update(id, request);
    }
}
