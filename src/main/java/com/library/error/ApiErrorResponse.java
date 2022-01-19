package com.library.error;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@NoArgsConstructor
@Getter
@Setter
public class ApiErrorResponse implements Serializable {
    private Integer status;
    private String path;
    private String message;
    private String exception;
    private Date timestamp;

    @JsonInclude(Include.NON_EMPTY)
    private List<ApiError> errors;

    private ApiErrorResponse(Integer status, String path, String message, String exception) {
        this.status = status;
        this.path = path;
        this.message = message;
        this.exception = exception;
    }

    public static ApiErrorResponse valueOf(Integer status, String path, String message, String exception) {
        return new ApiErrorResponse(status, path, message, exception);
    }
}
