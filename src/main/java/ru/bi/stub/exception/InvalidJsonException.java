package ru.bi.stub.exception;

public class InvalidJsonException extends RuntimeException {
  public InvalidJsonException(String message) {
    super(message);
  }
}
