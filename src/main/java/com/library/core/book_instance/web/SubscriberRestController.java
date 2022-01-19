package com.library.core.book_instance.web;

import com.library.core.book_instance.dto.SubscriberDto;
import com.library.core.book_instance.service.SubscriberService;
import com.library.core.book_instance.util.NotDeletedSubscriberSpecification;
import com.library.core.book_instance.web.request.SubscriberBaseRequest;
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
@RequestMapping("/subscriber")
public class SubscriberRestController {
    private final SubscriberService subscriberService;

    public SubscriberRestController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public SubscriberDto getOne(@PathVariable Long id) {
        return this.subscriberService.getSubscriber(id);
    }

    @GetMapping
    @ResponseBody
    public Page<SubscriberDto> getAll(
            @Or({
                    @Spec(path = "firstName", params = "q", spec = LikeIgnoreCase.class),
                    @Spec(path = "lastName", params = "q", spec = LikeIgnoreCase.class),
                    @Spec(path = "idCard", params = "q", spec = LikeIgnoreCase.class),
            }) NotDeletedSubscriberSpecification specification, @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return this.subscriberService.findAllSubscribers(specification, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public SubscriberDto create(@RequestBody @Valid SubscriberBaseRequest request) {
        return this.subscriberService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        this.subscriberService.delete(id);
    }

    @PutMapping("/{id}")
    public SubscriberDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid SubscriberBaseRequest request) {
        return this.subscriberService.update(id, request);
    }

}
