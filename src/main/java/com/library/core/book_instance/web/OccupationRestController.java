package com.library.core.book_instance.web;

import com.library.core.book_instance.service.OccupationService;
import com.library.core.book_instance.web.request.CreateInstanceReturnBaseRequest;
import com.library.core.book_instance.web.request.InstanceReturnBaseRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/occupation")
@AllArgsConstructor
public class OccupationRestController {
   private final OccupationService occupationService;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void instanceReturn(@RequestBody @Valid InstanceReturnBaseRequest request) {
        this.occupationService.instanceReturn(request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createOccupation(@RequestBody @Valid CreateInstanceReturnBaseRequest request) {
        this.occupationService.createOccupation(request);
    }
}
