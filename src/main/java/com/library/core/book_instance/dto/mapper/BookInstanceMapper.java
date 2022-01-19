package com.library.core.book_instance.dto.mapper;

import com.library.core.book_instance.domain.BookInstance;
import com.library.core.book_instance.domain.Occupation;
import com.library.core.book_instance.domain.Subscriber;
import com.library.core.book_instance.dto.BookInstanceDto;
import com.library.core.book_instance.dto.OccupationDto;
import com.library.core.book_instance.dto.SubscriberDto;
import com.library.core.book_instance.service.OccupationService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookInstanceMapper {
    BookInstanceMapper INSTANCE= Mappers.getMapper(BookInstanceMapper.class);

    @Mapping(target = "bookId",source = "book.id")
    @Mapping(target = "title",source = "book.title")
    @Mapping(target = "summary",source = "book.summary")
    @Mapping(target = "imprint",source = "book.imprint")
    @Mapping(target = "isbn",source = "book.isbn")
    @Mapping(target = "language",source = "book.language")
    @Mapping(target = "genres",source = "book.genres")
    @Mapping(target = "authors",source = "book.authors")
    BookInstanceDto mapToBookInstanceDto(BookInstance bookInstance, @Context OccupationService occupationService);

    @Mapping( target = "fullName", expression = "java(subscriber.getFirstName() + \" \" + subscriber.getLastName())")
    @Mapping(target = "occupations", ignore = true)
    SubscriberDto mapToSubscriberDto(Subscriber subscriber, @Context OccupationService occupationService);

    @AfterMapping
    default void mapToSubscriberDto(@MappingTarget SubscriberDto subscriberDto, Subscriber subscriber, @Context OccupationService occupationService) {
        var listOccupation = occupationService.getListOccupationActiveBySubscriber(subscriber);
        subscriberDto.setOccupations(listOccupation);
    }

    @Mapping(target = "uid",source = "bookInstance.uid")
    @Mapping(target = "bookId",source = "bookInstance.book.id")
    @Mapping(target = "title",source = "bookInstance.book.title")
    @Mapping(target = "summary",source = "bookInstance.book.summary")
    @Mapping(target = "imprint",source = "bookInstance.book.imprint")
    @Mapping(target = "isbn",source = "bookInstance.book.isbn")
    @Mapping(target = "language",source = "bookInstance.book.language")
    @Mapping(target = "genres",source = "bookInstance.book.genres")
    @Mapping(target = "authors",source = "bookInstance.book.authors")
    OccupationDto mapToOccupationDto(Occupation occupation);

}
