package com.library.core.book_instance.service;


import com.library.core.book_instance.domain.Subscriber;
import com.library.core.book_instance.dto.SubscriberDto;
import com.library.core.book_instance.web.request.SubscriberBaseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface SubscriberService {

    Subscriber findSubscriberOrThrow(Long id);

    SubscriberDto getSubscriber(Long id);

    Page<SubscriberDto> findAllSubscribers(Specification<Subscriber> specification, Pageable pageable);

    SubscriberDto create(SubscriberBaseRequest request);

    void delete(Long id);

    SubscriberDto update(Long id, SubscriberBaseRequest request);
}
