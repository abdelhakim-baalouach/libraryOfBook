package com.library.core.book_instance.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InstanceReturnBaseRequest {
    @NotNull
    private Long subscriberId;
    @NotNull
    private String instanceUid;
}
