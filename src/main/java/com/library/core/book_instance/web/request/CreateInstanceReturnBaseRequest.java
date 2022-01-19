package com.library.core.book_instance.web.request;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CreateInstanceReturnBaseRequest extends InstanceReturnBaseRequest {
    private OffsetDateTime dueBack;
}
