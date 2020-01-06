package com.douzone.bit.pathfinder.model.network;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {

  // api 통신시간
  private LocalDateTime transactionTime;

  // api 응답 코드
  private String resultCode;

  // api 부가 설명
  private Object description;

  private T data;

  // pagination
  private Pagination pagination;

  private Map<String, T> errorList;

  // OK
  @SuppressWarnings("unchecked")
  public static <T> Header<T> OK() {
    return (Header<T>) Header.builder().transactionTime(LocalDateTime.now()).resultCode("OK").description("OK").build();
  }

  // DATA OK
  @SuppressWarnings("unchecked")
  public static <T> Header<T> OK(T data) {
    return (Header<T>) Header.builder().transactionTime(LocalDateTime.now()).resultCode("OK").description("OK")
        .data(data).build();
  }

  @SuppressWarnings("unchecked")
  public static <T> Header<T> OK(T data, Pagination pagination) {

    return (Header<T>) Header.builder().transactionTime(LocalDateTime.now()).resultCode("OK").description("OK")
        .data(data).pagination(pagination).build();
  }

  // ERROR
  @SuppressWarnings("unchecked")
  public static <T> Header<T> ERROR(Object description) {
    return (Header<T>) Header.builder().transactionTime(LocalDateTime.now()).resultCode("ERROR")
        .description(description).build();
  }

  // ERROR
  @SuppressWarnings("unchecked")
  public static <T> Header<T> ERROR(Errors errors) {
    List<FieldError> errorList = errors.getFieldErrors();
    Map<String, Object> errorResult = new HashMap<>(errorList.size());

    errorList.forEach(error -> {
      errorResult.put(error.getField(), error.getDefaultMessage());
    });

    return (Header<T>) Header.builder().transactionTime(LocalDateTime.now()).resultCode("ERROR").errorList(errorResult)
        .build();
  }
}
