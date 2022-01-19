package com.library.core.book_instance.repository;

import com.library.core.book_instance.domain.BookInstance;
import com.library.core.book_instance.domain.Occupation;
import com.library.core.book_instance.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OccupationRepository extends JpaRepository<Occupation, Long> {
    List<Occupation> findAllBySubscriberAndActive(Subscriber subscriber, boolean isActive);
    List<Occupation> findAllByBookInstanceAndActive(BookInstance bookInstance, boolean isActive);
    Optional<Occupation> findByBookInstanceAndSubscriberAndActive(BookInstance bookInstance, Subscriber subscriber, boolean isActive);
}
