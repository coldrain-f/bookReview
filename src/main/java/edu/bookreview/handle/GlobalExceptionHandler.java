package edu.bookreview.handle;

import edu.bookreview.type.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 요청시 발생하는 예외
     * -> application/json 으로 요청해야하는 API 에 x-www-urlencoded 방식으로 요청했을 경우 발생
     * -> JSON 데이터를 API 스펙과 다르게 요청했을 경우 발생
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e, HttpServletRequest request) {

        log.error("handleHttpMessageNotReadableException", e);
        String path = request.getRequestURI();
        return ErrorResponse.of(path, ErrorCode.MISSING_REQUEST_BODY);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e, HttpServletRequest request) {

        log.error("handleHttpRequestMethodNotSupportedException", e);
        String path = request.getRequestURI();
        return ErrorResponse.of(path, ErrorCode.MISSING_REQUEST_BODY);
    }



    /**
     * @Valid 검증 실패시 발생하는 예외
     * -> Bean Validation 검증 실패시 발생
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request) {

        log.error("handleMethodArgumentNotValidException", e);
        String path = request.getRequestURI();
        return ErrorResponse.of(path, ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
    }

    /**
     * 처리하지 못한 모든 예외는 여기서 핸들링
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResponse handleException(Exception e, HttpServletRequest request) {
        log.error("handleException", e);
        String path = request.getRequestURI();
        return ErrorResponse.of(path, ErrorCode.INTERNAL_SERVER_ERROR);
    }

}