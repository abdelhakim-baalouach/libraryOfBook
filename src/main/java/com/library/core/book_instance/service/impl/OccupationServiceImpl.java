package com.library.core.book_instance.service.impl;

import com.library.core.book_instance.domain.BookInstance;
import com.library.core.book_instance.domain.Occupation;
import com.library.core.book_instance.domain.Subscriber;
import com.library.core.book_instance.dto.OccupationDto;
import com.library.core.book_instance.dto.mapper.BookInstanceMapper;
import com.library.core.book_instance.repository.OccupationRepository;
import com.library.core.book_instance.service.BookInstanceService;
import com.library.core.book_instance.service.OccupationService;
import com.library.core.book_instance.service.SubscriberService;
import com.library.core.book_instance.web.request.CreateInstanceReturnBaseRequest;
import com.library.core.book_instance.web.request.InstanceReturnBaseRequest;
import com.library.error.EntityNotFoundException;
import com.library.util.MessageUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OccupationServiceImpl implements OccupationService {
    private final OccupationRepository occupationRepository;
    private final SubscriberService subscriberService;
    private final BookInstanceService bookInstanceService;
    private final MessageUtil messageUtil;
    private final BookInstanceMapper bookInstanceMapper;

    public OccupationServiceImpl(
            @Lazy OccupationRepository occupationRepository,
            @Lazy SubscriberService subscriberService,
            BookInstanceService bookInstanceService,
            MessageUtil messageUtil,
            BookInstanceMapper bookInstanceMapper
    ) {
        this.occupationRepository = occupationRepository;
        this.subscriberService = subscriberService;
        this.bookInstanceService = bookInstanceService;
        this.messageUtil = messageUtil;
        this.bookInstanceMapper = bookInstanceMapper;
    }

    @Override
    public void create(BookInstance bookInstance) {
        this.occupationRepository.saveAndFlush(new Occupation(bookInstance));
    }

    @Override
    public List<OccupationDto> getListOccupationActiveBySubscriber(Subscriber subscriber) {
        return this.occupationRepository
                .findAllBySubscriberAndActive(subscriber, true)
                .stream()
                .map(this::mapToOccupationDto)
                .collect(Collectors.toList());
    }

    @Override
    public void instanceReturn(InstanceReturnBaseRequest request) {
        var instance = this.bookInstanceService.findBookInstanceOrThrow(request.getInstanceUid());
        var subscriber = this.subscriberService.findSubscriberOrThrow(request.getSubscriberId());
        this.inactiveInstance(instance, subscriber);
        this.create(instance);
    }

    @Override
    public Occupation getOccupationActiveByBookInstanceAndSubscriber(BookInstance bookInstance, Subscriber subscriber) {
        return this.occupationRepository.findByBookInstanceAndSubscriberAndActive(bookInstance, subscriber, true)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("occupation.NotFound", bookInstance.getUid() , subscriber.getId())));
    }

    @Override
    public void createOccupation(CreateInstanceReturnBaseRequest request) {
        var instance = this.bookInstanceService.findBookInstanceOrThrow(request.getInstanceUid());
        var subscriber = this.subscriberService.findSubscriberOrThrow(request.getSubscriberId());
        this.inactiveInstance(instance, null);
        var occupation = new Occupation(instance, subscriber, request.getDueBack());
        this.occupationRepository.save(occupation);
    }

    private void inactiveInstance(BookInstance bookInstance, Subscriber subscriber) {
        var occupation = this.getOccupationActiveByBookInstanceAndSubscriber(bookInstance, subscriber);
        occupation.setActive(false);
        this.occupationRepository.save(occupation);
    }

    private OccupationDto mapToOccupationDto(Occupation occupation) {
        return this.bookInstanceMapper.mapToOccupationDto(occupation);
    }
}
