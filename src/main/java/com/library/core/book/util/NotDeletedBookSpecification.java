package com.library.core.book.util;

import com.library.core.book.domain.Book;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = "state", constVal = "ACTIVE", spec = Equal.class)
public interface NotDeletedBookSpecification extends Specification<Book> {
}
