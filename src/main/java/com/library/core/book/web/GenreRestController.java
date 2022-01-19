package com.library.core.book.web;

import com.library.core.book.dto.GenreOrLanguageDto;
import com.library.core.book.service.GenreService;
import com.library.core.book.util.NotDeletedGenreSpecification;
import com.library.core.book.web.request.GenreOrLanguageBaseRequest;
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
@RequestMapping("/genre")
public class GenreRestController {
    private final GenreService genreService;

    public GenreRestController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public GenreOrLanguageDto getOne(@PathVariable Long id) {
        return this.genreService.getGenre(id);
    }

    @GetMapping
    @ResponseBody
    public Page<GenreOrLanguageDto> getAll(
            @Or({
                    @Spec(path = "name", params = "q", spec = LikeIgnoreCase.class),
            }) NotDeletedGenreSpecification specification, @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return this.genreService.findAllGenres(specification, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public GenreOrLanguageDto create(@RequestBody @Valid GenreOrLanguageBaseRequest request) {
        return this.genreService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        this.genreService.delete(id);
    }

    @PutMapping("/{id}")
    public GenreOrLanguageDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid GenreOrLanguageBaseRequest request) {
        return this.genreService.update(id, request);
    }
}
