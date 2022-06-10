package edu.bookreview.handle;

import edu.bookreview.type.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String message;
    private int status;
    private List<FieldError> errors;
    private String code;
    private LocalDateTime timestamp;

    public ErrorResponse(String message, int status, String code) {
        this.message = message;
        this.status = status;
        this.errors = new ArrayList<>();
        this.code = code;
        this.timestamp = LocalDateTime.now();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;
    }

    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(code.getMessage(), code.getStatus(), code.getCode());
    }
}
