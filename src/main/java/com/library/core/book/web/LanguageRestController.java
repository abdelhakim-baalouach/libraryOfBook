package com.library.core.book.web;

import com.library.core.book.dto.GenreOrLanguageDto;
import com.library.core.book.service.LanguageService;
import com.library.core.book.util.NotDeletedLanguageSpecification;
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
@RequestMapping("/language")
public class LanguageRestController {
    private final LanguageService languageService;

    public LanguageRestController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public GenreOrLanguageDto getOne(@PathVariable Long id) {
        return this.languageService.getLanguage(id);
    }

    @GetMapping
    @ResponseBody
    public Page<GenreOrLanguageDto> getAll(
            @Or({
                    @Spec(path = "name", params = "q", spec = LikeIgnoreCase.class),
            }) NotDeletedLanguageSpecification specification, @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return this.languageService.findAllLanguage(specification, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public GenreOrLanguageDto create(@RequestBody @Valid GenreOrLanguageBaseRequest request) {
        return this.languageService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        this.languageService.delete(id);
    }

    @PutMapping("/{id}")
    public GenreOrLanguageDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid GenreOrLanguageBaseRequest request) {
        return this.languageService.update(id, request);
    }
}
