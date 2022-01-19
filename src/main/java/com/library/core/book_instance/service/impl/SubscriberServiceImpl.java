package com.library.core.book_instance.service.impl;

import com.library.core.book_instance.domain.Subscriber;
import com.library.core.book_instance.dto.SubscriberDto;
import com.library.core.book_instance.dto.mapper.BookInstanceMapper;
import com.library.core.book_instance.repository.SubscriberRepository;
import com.library.core.book_instance.service.OccupationService;
import com.library.core.book_instance.service.SubscriberService;
import com.library.core.book_instance.web.request.SubscriberBaseRequest;
import com.library.error.EntityNotFoundException;
import com.library.util.MessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class SubscriberServiceImpl implements SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final OccupationService occupationService;
    private final MessageUtil messageUtil;
    private final BookInstanceMapper bookInstanceMapper;

    @Override
    public Subscriber findSubscriberOrThrow(Long id) {
        return this.subscriberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("subscriber.NotFound", id)));
    }

    @Override
    public SubscriberDto getSubscriber(Long id) {
        var subscriber = this.findSubscriberOrThrow(id);
        return this.mapToSubscriberDto(subscriber);
    }

    @Override
    public Page<SubscriberDto> findAllSubscribers(Specification<Subscriber> specification, Pageable pageable) {
        return this.subscriberRepository
                .findAll(specification, pageable)
                .map(this::mapToSubscriberDto);
    }

    @Override
    public SubscriberDto create(SubscriberBaseRequest request) {
        var subscriber = new Subscriber(request);
        return this.mapToSubscriberDto(this.save(subscriber));
    }

    @Override
    public void delete(Long id) {
        try {
            this.subscriberRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("subscriber.NotFound", id));
        }
    }

    @Override
    public SubscriberDto update(Long id, SubscriberBaseRequest request) {
        var subscriberSelected = this.findSubscriberOrThrow(id);
        var subscriber = this.of(subscriberSelected, request);
        return this.mapToSubscriberDto(this.subscriberRepository.saveAndFlush(subscriber));
    }

    private Subscriber of(Subscriber subscriberSelected, SubscriberBaseRequest request) {
        subscriberSelected.setLastName(request.getLastName());
        subscriberSelected.setFirstName(request.getFirstName());
        subscriberSelected.setDateOfbirth(request.getDateOfbirth());
        subscriberSelected.setEmail(request.getEmail());
        subscriberSelected.setIdCard(request.getIdCard());
        subscriberSelected.setPhone(request.getPhone());
        return subscriberSelected;
    }

    private Subscriber save(Subscriber subscriber) {
        return this.subscriberRepository.saveAndFlush(subscriber);
    }

    private SubscriberDto mapToSubscriberDto(Subscriber subscriber) {
        return this.bookInstanceMapper.mapToSubscriberDto(subscriber, occupationService);
    }
}
