package com.library.core.book_instance.service;

import com.library.core.book_instance.domain.BookInstance;
import com.library.core.book_instance.domain.Occupation;
import com.library.core.book_instance.domain.Subscriber;
import com.library.core.book_instance.dto.OccupationDto;
import com.library.core.book_instance.web.request.CreateInstanceReturnBaseRequest;
import com.library.core.book_instance.web.request.InstanceReturnBaseRequest;

import java.util.List;

public interface OccupationService {

    void create(BookInstance bookInstance);

    List<OccupationDto> getListOccupationActiveBySubscriber(Subscriber subscriber);

    void instanceReturn(InstanceReturnBaseRequest request);
    
    Occupation getOccupationActiveByBookInstanceAndSubscriber(BookInstance bookInstance, Subscriber subscriber);

    void createOccupation(CreateInstanceReturnBaseRequest request);
}
